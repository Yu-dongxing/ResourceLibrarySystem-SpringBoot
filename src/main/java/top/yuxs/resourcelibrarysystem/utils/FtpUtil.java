package top.yuxs.resourcelibrarysystem.utils;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static cn.dev33.satoken.SaManager.log;

@Component
public class FtpUtil {
    @Value("${ftp.host}")
    private String host;

    @Value("${ftp.port}")
    private int port;

    @Value("${ftp.username}")
    private String username;

    @Value("${ftp.password}")
    private String password;

    @Autowired
    private FTPClient ftpClient;

    /**
     * 上传文件
     * @param remotePath 远程目录（如：/upload）
     * @param fileName   文件名
     * @param input      文件流
     */
    public boolean uploadFile(String remotePath, String fileName, InputStream input, int maxRetries) throws IOException {
        int retryCount = 0;
        while (retryCount <= maxRetries) {
            try {
                // 切换到根目录（确保从根目录开始创建路径）
                if (!ftpClient.changeWorkingDirectory("/")) {
                    throw new IOException("无法切换到根目录");
                }

                // 递归创建多级目录
                if (StringUtils.hasLength(remotePath)) {
                    String[] paths = remotePath.split("/");
                    for (String path : paths) {
                        if (!StringUtils.hasLength(path)) continue; // 跳过空路径
                        if (!ftpClient.changeWorkingDirectory(path)) {
                            if (!ftpClient.makeDirectory(path)) {
                                throw new IOException("创建目录失败: " + path + "，完整路径：" + remotePath);
                            }
                            if (!ftpClient.changeWorkingDirectory(path)) {
                                throw new IOException("切换到目录失败: " + path + "，完整路径：" + remotePath);
                            }
                        }
                    }
                }

                // 上传文件
                boolean success = ftpClient.storeFile(fileName, input);
                if (success) {
                    return true;
                } else {
                    throw new IOException("文件上传失败，服务器响应：" + ftpClient.getReplyString());
                }
            } catch (IOException e) {
                retryCount++;
                if (retryCount > maxRetries) {
                    throw new IOException("文件上传失败，达到最大重试次数：" + maxRetries + "，异常信息：" + e.getMessage(), e);
                }
                log.error("文件上传失败，尝试重新连接FTP服务器，重试次数：" + retryCount, e);
                // 关闭当前连接并重新连接
                disconnect();
                connect();
            } finally {
                if (input != null) {
                    try {
                        input.close();
                    } catch (IOException e) {
                        e.printStackTrace(); // 记录关闭流时的异常
                    }
                }
            }
        }
        return false;
    }
    /**
     * 删除文件
     * @param remotePath 文件所在的远程目录（如：/2025/02/10）
     * @param fileName   文件名
     * @return 删除是否成功
     */
    public boolean deleteFile(String remotePath, String fileName) throws IOException {
        try {
            // 切换到目标目录
            if (!ftpClient.changeWorkingDirectory(remotePath)) {
                throw new IOException("无法切换到目录: " + remotePath);
            }

            // 删除文件
            return ftpClient.deleteFile(fileName);
        } catch (IOException e) {
            throw new IOException("删除文件失败: " + remotePath + "/" + fileName, e);
        }
    }
    /**
     * 下载文件
     * @param remoteFilePath 远程文件路径（如：/upload/test.txt）
     * @param output         输出流
     */
    public boolean downloadFile(String remoteFilePath, OutputStream output) throws IOException {
        InputStream input = null;
        try {
            input = ftpClient.retrieveFileStream(remoteFilePath);
            if (input == null) return false;
            IOUtils.copy(input, output);
            return true;
        } finally {
            IOUtils.closeQuietly(input);
            IOUtils.closeQuietly(output);
            ftpClient.completePendingCommand();
        }
    }


    /**
     * 关闭FTP连接
     */
    public void disconnect() {
        if (ftpClient != null && ftpClient.isConnected()) {
            try {
                ftpClient.logout();
                ftpClient.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * 重新连接FTP服务器
     */
    public void connect() throws IOException {
        if (ftpClient.isConnected()) {
            return; // 如果已经连接，则直接返回
        }
        // 在这里添加FTP服务器的连接逻辑，例如：
        ftpClient.connect(host,port); // 替换为实际的FTP服务器地址和端口
        int replyCode = ftpClient.getReplyCode();
        if (!FTPReply.isPositiveCompletion(replyCode)) {
            throw new IOException("FTP服务器连接失败，响应码：" + replyCode);
        }
        ftpClient.login(username, password); // 替换为实际的用户名和密码
        ftpClient.enterLocalPassiveMode(); // 使用被动模式
    }
}