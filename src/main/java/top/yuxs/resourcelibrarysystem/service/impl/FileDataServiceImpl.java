package top.yuxs.resourcelibrarysystem.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.yuxs.resourcelibrarysystem.mapper.FileDataMapper;
import top.yuxs.resourcelibrarysystem.pojo.FileData;
import top.yuxs.resourcelibrarysystem.service.FileDataService;

import java.util.List;

@Service
public class FileDataServiceImpl implements FileDataService {
    @Autowired
    private FileDataMapper fileDataMapper;

    @Override
    public void add(FileData fileData) {
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
}
