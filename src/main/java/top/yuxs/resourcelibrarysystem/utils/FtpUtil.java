package top.yuxs.resourcelibrarysystem.utils;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.*;
import java.util.UUID;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Log4j2
@Component
public class FtpUtil {
    @Value("${ftp.host}")
    private String host;

    @Value("${ftp.port:21}")
    private int port;

    @Value("${ftp.username}")
    private String username;

    @Value("${ftp.password}")
    private String password;

    @Value("${ftp.connectTimeout:5000}")
    private int connectTimeout;

    @Value("${ftp.dataTimeout:30000}")
    private int dataTimeout;

    private final Lock lock = new ReentrantLock();
    private FTPClient ftpClient;

    /**
     * 上传文件（支持断点续传和分块上传）
     */
    public boolean uploadFile(String remotePath, String fileName, InputStream input,
                              int maxRetries, int blockSize) throws IOException, InterruptedException {
        lock.lock();
        try {
            int retryCount = 0;
            while (retryCount <= maxRetries) {
                try {
                    if (!ensureConnected()) throw new IOException("FTP连接失败");

                    createDirectories(remotePath);

                    if (blockSize > 0) {
                        uploadWithStreaming(fileName, input, blockSize);
                    } else {
                        if (!ftpClient.storeFile(fileName, input)) {
                            throw new IOException("上传失败: " + ftpClient.getReplyString());
                        }
                    }
                    return true;
                } catch (IOException e) {
                    if (retryCount++ >= maxRetries) throw e;
                    log.warn("上传失败，第{}次重试（共{}次）", retryCount, maxRetries, e);
                    resetConnection();
                    Thread.sleep(1000L * retryCount);
                }
            }
            return false;
        } finally {
            closeQuietly(input);
            lock.unlock();
        }
    }

    /**
     * 流式分块上传（内存优化）
     */
    private void uploadWithStreaming(String fileName, InputStream input, int blockSize) throws IOException {
        try (OutputStream os = ftpClient.storeFileStream(fileName)) {
            if (os == null) throw new IOException("无法获取输出流: " + ftpClient.getReplyString());

            byte[] buffer = new byte[blockSize];
            int bytesRead;
            long totalUploaded = 0;
            while ((bytesRead = input.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
                totalUploaded += bytesRead;
                log.debug("已上传 {} bytes", totalUploaded);
            }
        } finally {
            if (!ftpClient.completePendingCommand()) {
                throw new IOException("传输未完成: " + ftpClient.getReplyString());
            }
        }
    }

    /**
     * 安全更新文件（原子操作）
     */
    public boolean atomicUpdate(String oldPath, String oldFile,
                                String newPath, String newFile,
                                InputStream input, int retries, int blockSize) throws Exception {
        String tempFile = "tmp_" + UUID.randomUUID();
        try {
            if (!uploadFile(newPath, tempFile, input, retries, blockSize)) return false;

            if (!renameFile(newPath, tempFile, newFile)) {
                deleteFile(newPath, tempFile);
                return false;
            }

            if (StringUtils.hasText(oldPath) && StringUtils.hasText(oldFile)) {
                if (!deleteFile(oldPath, oldFile)) {
                    log.warn("旧文件删除失败: {}/{}", oldPath, oldFile);
                }
            }
            return true;
        } catch (Exception e) {
            deleteFile(newPath, tempFile);
            throw e;
        }
    }

    /**
     * 增强型目录创建
     */
    private void createDirectories(String path) throws IOException {
        if (!ftpClient.changeWorkingDirectory("/"))
            throw new IOException("无法访问根目录");

        if (!StringUtils.hasLength(path)) return;

        for (String dir : path.split("/")) {
            if (dir.isEmpty()) continue;
            if (!ftpClient.changeWorkingDirectory(dir)) {
                if (!ftpClient.makeDirectory(dir))
                    throw new IOException("目录创建失败: " + dir);
                if (!ftpClient.changeWorkingDirectory(dir))
                    throw new IOException("目录切换失败: " + dir);
            }
        }
    }

    /**
     * 智能连接管理
     */
    private boolean ensureConnected() throws IOException {
        if (isConnectionAlive()) return true;

        ftpClient = new FTPClient();
        ftpClient.setConnectTimeout(connectTimeout);
        ftpClient.setDataTimeout(dataTimeout);

        ftpClient.connect(host, port);
        if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode()))
            throw new IOException("连接被拒绝: " + ftpClient.getReplyString());

        if (!ftpClient.login(username, password))
            throw new IOException("认证失败: " + ftpClient.getReplyString());

        ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
        ftpClient.enterLocalPassiveMode();
        ftpClient.setControlEncoding("UTF-8");
        return true;
    }

    private boolean isConnectionAlive() {
        try {
            return ftpClient != null && ftpClient.isConnected()
                    && ftpClient.sendNoOp();
        } catch (IOException e) {
            return false;
        }
    }

    private void resetConnection() {
        try {
            if (ftpClient != null && ftpClient.isConnected()) {
                ftpClient.logout();
                ftpClient.disconnect();
            }
        } catch (IOException e) {
            log.error("连接重置异常", e);
        }
    }

    // 其他工具方法（下载、删除、重命名等）
    public boolean downloadFile(String remotePath, OutputStream output) throws IOException {
        lock.lock();
        InputStream is = null;
        try {
            ensureConnected();
            is = ftpClient.retrieveFileStream(remotePath);
            if (is == null) return false;

            IOUtils.copy(is, output);
            return ftpClient.completePendingCommand();
        } finally {
            closeQuietly(is);
            lock.unlock();
        }
    }

    public boolean deleteFile(String path, String filename) throws IOException {
        lock.lock();
        try {
            ensureConnected();
            ftpClient.changeWorkingDirectory(path);
            return ftpClient.deleteFile(filename);
        } finally {
            lock.unlock();
        }
    }

    public boolean renameFile(String path, String oldName, String newName) throws IOException {
        lock.lock();
        try {
            ensureConnected();
            ftpClient.changeWorkingDirectory(path);
            return ftpClient.rename(oldName, newName);
        } finally {
            lock.unlock();
        }
    }

    private void closeQuietly(Closeable closeable) {
        try {
            if (closeable != null) closeable.close();
        } catch (IOException e) {
            log.error("资源关闭异常", e);
        }
    }
}