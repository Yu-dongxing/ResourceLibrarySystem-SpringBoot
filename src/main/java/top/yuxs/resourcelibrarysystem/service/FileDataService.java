package top.yuxs.resourcelibrarysystem.service;

import top.yuxs.resourcelibrarysystem.pojo.FileData;

import java.util.List;

public interface FileDataService {
    void add(FileData fileData);

    FileData getById(Long fileId);

    void removeById(Long fileId);

    List<FileData> getByResourceId(String ResourceId);
}
