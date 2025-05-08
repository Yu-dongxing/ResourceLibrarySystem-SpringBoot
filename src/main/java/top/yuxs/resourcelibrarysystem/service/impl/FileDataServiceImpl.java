package top.yuxs.resourcelibrarysystem.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import top.yuxs.resourcelibrarysystem.mapper.FileDataMapper;
import top.yuxs.resourcelibrarysystem.pojo.FileData;
import top.yuxs.resourcelibrarysystem.service.FileDataService;
import top.yuxs.resourcelibrarysystem.utils.FtpUtil;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;


@Slf4j
@Service
//文件数据服务
public class FileDataServiceImpl implements FileDataService{
    @Value("${upload.temp.dir:${java.io.tmpdir}/file-uploads}")
    private String tempDir;
    @Autowired
    private static final String TEMP_DIR = System.getProperty("java.io.tmpdir");

    @Value("${File-Proxy-Website.url}")
    private String url;

    @Autowired
    private FtpUtil ftpUtil;

    @Autowired
    private FileDataMapper fileDataMapper;

    @Override
    public void add(FileData fileData) {
        fileDataMapper.insertFileData(fileData);
    }
    @Override
    public void fileDataAdd(FileData fileData) {
        fileData.setIsDeleted(0);
        fileData.setUploadTime(LocalDateTime.now());
        fileData.setUserName((String) StpUtil.getExtra("username"));
        fileDataMapper.insertFileData(fileData);
    }

    @Override
    public FileData getById(Long fileId) {
        return fileDataMapper.findById(fileId);
    }

    @Override
    public void removeById(Long fileId) {
        fileDataMapper.deleteFileData(fileId);
    }

    @Override
    public List<FileData> getByResourceId(String ResourceId) {
        return fileDataMapper.findAllByUuid(ResourceId);
    }

    /**
     * 保存分片文件到临时目录
     */
    @Override
    public void saveChunk(
            MultipartFile file,
            String fileMd5, // 为文件的MD5值
            String FileAssociationId, // 文件关联id
            String fileName,
            int chunkNumber,
            int totalChunks
    ) throws IOException {
        // 使用MD5值作为临时目录名
        String chunkDir = Paths.get(tempDir, fileMd5).toString();
        File dir = new File(chunkDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        // 分片文件名格式：MD5_chunk_%d.tmp
        String chunkFilename = String.format("%s_chunk_%d.tmp", fileMd5, chunkNumber);
        File chunkFile = new File(dir, chunkFilename);
        file.transferTo(chunkFile);
    }


    /**
     * 合并分片文件并上传到 FTP
     */
    @Override
    public void mergeChunksAndUpload(
            String fileMd5,
            String fileName,
            String FileAssociationId, // 文件关联id
            int totalChunks
    ) throws IOException {
        String chunkDir = Paths.get(tempDir, fileMd5).toString();
        File[] chunks = new File(chunkDir).listFiles();
        if (chunks == null || chunks.length != totalChunks) {
            throw new IOException("Missing chunks");
        }
        // 按分片编号排序，调整split索引以适配新的文件名格式
        Arrays.sort(chunks, Comparator.comparingInt(f ->
                Integer.parseInt(f.getName().split("_")[2].split("\\.")[0])
        ));
        File outputFile = new File(tempDir, fileName);
        try (FileOutputStream fos = new FileOutputStream(outputFile)) {
            for (File chunk : chunks) {
                Files.copy(chunk.toPath(), fos);
            }
        }
        // --- 新增上传逻辑 ---
        boolean uploadSuccess = false;
        try (InputStream fileInputStream = new FileInputStream(outputFile)) { // 自动关闭流
            //获取文件后缀名
            String fileType = getFileExtension(fileName,true);
            // 生成远程文件名（示例用UUID，可按需修改）
            String remoteFileName = UUID.randomUUID()+fileType;
            //获取上传路径
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            String remotePath = "/" + now.format(formatter);
            // 调用FTP上传工具
            uploadSuccess = ftpUtil.uploadFile(
                    remotePath,             // 远程路径（按需修改）
                    remoteFileName,         // 远程文件名
                    fileInputStream,        // 文件输入流
                    3,                      // 重试次数
                    500000                  // 缓冲区大小
            );
            if(!uploadSuccess) {
                throw new IOException("FTP upload failed after retries");
            }
            //调用文件数据
            FileData fileData = new FileData();
            //获取文件数据
            long fileSize = Files.size(outputFile.toPath());
            fileData.setFileSize(fileSize);
            fileData.setFileType(fileType);
            fileData.setFileMd5(fileMd5);
            fileData.setFileName(fileName);
            fileData.setUuidFileName(remoteFileName);
            fileData.setFilePath(remotePath);
            fileData.setFileUrl(url+remotePath+'/'+remoteFileName);
            fileData.setResourceId(FileAssociationId);
            fileDataAdd(fileData);

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            // 无论上传成功与否都清理临时文件
            FileUtils.deleteDirectory(new File(chunkDir));
            if (outputFile.exists()) {
                outputFile.delete();
            }
        }
        // --- 上传逻辑结束 ---
        log.info("File uploaded successfully: {}", fileName);
    }

    @Override
    public List<FileData> findAll() {
        return fileDataMapper.findAll();
    }


    /**
     * 获取分片总数
     */
    private int getTotalChunks(String fileName) throws IOException {
        return (int) Files.list(Paths.get(TEMP_DIR))
                .filter(path -> path.getFileName().toString().startsWith(fileName + "-"))
                .count();
    }

    /**
     * 生成分片文件名
     */
    private String generateChunkFileName(String fileName, int chunkNumber) {
        return fileName + "-" + chunkNumber;
    }
    /**
     * 获取文件后缀名
     * @param fileName 文件名
     * @param isdi(true,false) 返回的后缀名是否带  .
     * @return 文件后缀名（如果isdi为true 带点，如 .txt；isdi为false则不带点，如txt）
     */
    private String getFileExtension(String fileName, boolean isdi) {
        if (fileName == null || fileName.isEmpty()) {
            return "";
        }
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex > 0 && lastDotIndex < fileName.length() - 1) {
            if (isdi) {
                // 如果isdi为true，返回带点的后缀名
                return fileName.substring(lastDotIndex);
            } else {
                // 如果isdi为false，返回不带点的后缀名
                return fileName.substring(lastDotIndex + 1);
            }
        }
        return "";
    }
}
