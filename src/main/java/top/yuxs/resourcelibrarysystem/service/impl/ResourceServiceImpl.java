package top.yuxs.resourcelibrarysystem.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.yuxs.resourcelibrarysystem.DTO.ResourceUpdateDto;
import top.yuxs.resourcelibrarysystem.mapper.ResourceMapper;
import top.yuxs.resourcelibrarysystem.pojo.Resource;
import top.yuxs.resourcelibrarysystem.service.ResourceService;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ResourceServiceImpl  implements ResourceService {
    @Autowired
    private ResourceMapper resourceMapper;
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

//    @Override
//    public Resource logicDeleteById(Integer id) {
//        return null;
//    }


}
