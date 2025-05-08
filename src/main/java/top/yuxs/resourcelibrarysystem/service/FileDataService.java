package top.yuxs.resourcelibrarysystem.service;

import org.springframework.web.multipart.MultipartFile;
import top.yuxs.resourcelibrarysystem.pojo.FileData;

import java.io.IOException;
import java.util.List;

public interface FileDataService{
    void add(FileData fileData);

    void fileDataAdd(FileData fileData);

    FileData getById(Long fileId);

    void removeById(Long fileId);

    List<FileData> getByResourceId(String ResourceId);

    void saveChunk(
            MultipartFile file,
            String fileMd5, // identifier应为文件的MD5值
            String FileAssociationId, // 文件关联id
            String fileName,
            int chunkNumber,
            int totalChunks
    ) throws IOException;

    void mergeChunksAndUpload(
            String fileMd5,
            String fileName,
            String FileAssociationId, // 文件关联id
            int totalChunks
    ) throws IOException;

    List<FileData> findAll();
}
