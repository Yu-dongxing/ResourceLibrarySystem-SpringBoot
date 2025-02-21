package top.yuxs.resourcelibrarysystem.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.yuxs.resourcelibrarysystem.DTO.GetResourceFileListDTO;
import top.yuxs.resourcelibrarysystem.DTO.ResourceCompleteUpdateDTO;
import top.yuxs.resourcelibrarysystem.DTO.ResourceFileDTO;
import top.yuxs.resourcelibrarysystem.DTO.ResourceUpdateDto;
import top.yuxs.resourcelibrarysystem.mapper.FileDataMapper;
import top.yuxs.resourcelibrarysystem.mapper.ResourceMapper;
import top.yuxs.resourcelibrarysystem.pojo.FileData;
import top.yuxs.resourcelibrarysystem.pojo.Resource;
import top.yuxs.resourcelibrarysystem.service.ResourceService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResourceServiceImpl  implements ResourceService {
    @Autowired
    private ResourceMapper resourceMapper;

    @Autowired
    private FileDataMapper fileDataMapper;
    @Override
    public void add(Resource resource,String name) {
        resource.setCreateTime(LocalDateTime.now());
        resource.setUpdateTime(LocalDateTime.now());
        resource.setAuthor(name);
        resourceMapper.insert(resource);
    }

    @Override
    public List<Resource> list() {
        return resourceMapper.findAll();
    }

    @Override
    public void logicDelete(Long id) {
        resourceMapper.logicDeleteById(id);
    }

    @Override
    public void update(ResourceUpdateDto resourceUpdateDto, String name,Long id) {
        Resource resource = resourceMapper.findById(id);
        if(resource==null){
            throw new RuntimeException("资源不存在");
        }else {
            resource.setUpdateTime(LocalDateTime.now());

            resource.setImg(resourceUpdateDto.getImg());
            resource.setName(resourceUpdateDto.getName());
            resource.setAuthor(name);
            resource.setUrl(resourceUpdateDto.getUrl());
            resource.setTab(resourceUpdateDto.getTab());
            resourceMapper.update(resource);
        }

    }

    @Override
    public List<Resource> search(String keyword, String category, String author, String startTime, String endTime) {
        return resourceMapper.search(keyword, category, author, startTime, endTime);
    }

    @Override
    public List<Resource> searchByKeyword(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return list();
        }
        return resourceMapper.searchByKeyword("%" + keyword + "%");
    }

    @Override
    public List<Resource> searchByCategory(String category) {
        if (category == null || category.trim().isEmpty()) {
            return list();
        }
        return resourceMapper.searchByCategory(category);
    }

    @Override
    public List<Resource> searchByAuthor(String author) {
        if (author == null || author.trim().isEmpty()) {
            return list();
        }
        return resourceMapper.searchByAuthor(author);
    }

    @Override
    public List<Resource> searchByTimeRange(String startTime, String endTime) {
        return resourceMapper.searchByTimeRange(startTime, endTime);
    }

    @Override
    public Resource selectById(long id) {
        return resourceMapper.findById(id);
    }

    @Override
    public List<Resource> selectAudit() {
        return resourceMapper.searchAuditByAuditId();
    }

    @Override
    public void auditById(Long id) {
        resourceMapper.auditById(id);
    }

    @Override
    public void addFileResource(ResourceFileDTO data,String name) {
        Resource resource = new Resource();
        resource.setResourceFileId(data.getResourceFileId());
        resource.setImg(data.getImg());
        resource.setDesc(data.getDesc());
        resource.setName(data.getName());
        resource.setUrl(data.getUrl());
        resource.setTab("文件");
        resource.setAuthor(name);
        resource.setUpdateTime(LocalDateTime.now());
        resource.setCreateTime(LocalDateTime.now());
        resourceMapper.insert(resource);
    }

    @Override
    public List<GetResourceFileListDTO> resourceFileList() {
        List<Resource> resourceList = resourceMapper.findAll();
        return resourceList.stream().map(resource -> {
            GetResourceFileListDTO getResourceFileListDTO = new GetResourceFileListDTO();
            getResourceFileListDTO.setName(resource.getName());
            getResourceFileListDTO.setImg(resource.getImg());
            getResourceFileListDTO.setUrl(resource.getUrl());
            getResourceFileListDTO.setAuthor(resource.getAuthor());
            getResourceFileListDTO.setTab(resource.getTab());
            getResourceFileListDTO.setDesc(resource.getDesc());
            getResourceFileListDTO.setUpdateTime(resource.getUpdateTime());
            getResourceFileListDTO.setCreateTime(resource.getCreateTime());
            getResourceFileListDTO.setId(resource.getId());
            getResourceFileListDTO.setResourceFileId(resource.getResourceFileId());
            List<FileData> fileData = fileDataMapper.findAllByUuid(resource.getResourceFileId());
            if (fileData != null) {
                getResourceFileListDTO.setFileData(fileData);
            } else {
                getResourceFileListDTO.setFileData(List.of());
            }

            return getResourceFileListDTO;
        }).collect(Collectors.toList());
    }

    @Override
    public GetResourceFileListDTO selectResourceFileByID(long id) {
        Resource resource = resourceMapper.findById(id);
        GetResourceFileListDTO getResourceFileListDTO = new GetResourceFileListDTO();
        getResourceFileListDTO.setId(resource.getId());
        getResourceFileListDTO.setName(resource.getName());
        getResourceFileListDTO.setUpdateTime(resource.getUpdateTime());
        getResourceFileListDTO.setTab(resource.getTab());
        getResourceFileListDTO.setUrl(resource.getUrl());
        getResourceFileListDTO.setImg(resource.getImg());
        getResourceFileListDTO.setAuthor(resource.getAuthor());
        getResourceFileListDTO.setCreateTime(resource.getCreateTime());
        getResourceFileListDTO.setResourceFileId(resource.getResourceFileId());
        getResourceFileListDTO.setDesc(resource.getDesc());
        List<FileData> fileData = fileDataMapper.findAllByUuid(resource.getResourceFileId());
        getResourceFileListDTO.setFileData(fileData);
        return getResourceFileListDTO;
    }

    @Override
    public void completeUpdate(Long id, ResourceCompleteUpdateDTO updateData, String username) {

    }

//    @Override
//    public Resource logicDeleteById(Integer id) {
//        return null;
//    }


}
