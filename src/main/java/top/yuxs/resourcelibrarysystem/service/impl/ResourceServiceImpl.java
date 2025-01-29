package top.yuxs.resourcelibrarysystem.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
    public void add(Resource resource) {
        resource.setCreateTime(LocalDateTime.now());
        resource.setUpdateTime(LocalDateTime.now());
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
    public void update(Resource resource) {
        resource.setUpdateTime(LocalDateTime.now());
        resourceMapper.update(resource);
    }

//    @Override
//    public Resource logicDeleteById(Integer id) {
//        return null;
//    }


}
