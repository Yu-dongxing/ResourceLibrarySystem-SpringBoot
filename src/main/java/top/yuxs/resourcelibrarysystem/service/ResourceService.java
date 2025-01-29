package top.yuxs.resourcelibrarysystem.service;

import top.yuxs.resourcelibrarysystem.pojo.Resource;

import java.util.List;

public interface ResourceService {
    void add(Resource resource);

    List<Resource> list();

    void logicDelete(Long id);


    void update(Resource resource);
}
