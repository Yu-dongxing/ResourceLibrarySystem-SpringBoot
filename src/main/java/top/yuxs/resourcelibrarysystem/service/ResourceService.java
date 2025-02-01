package top.yuxs.resourcelibrarysystem.service;

import top.yuxs.resourcelibrarysystem.pojo.Resource;

import java.util.List;

public interface ResourceService {
    void add(Resource resource,String name);

    List<Resource> list();

    void logicDelete(Long id);


    void update(Resource resource, String name);

    List<Resource> search(String keyword, String category, String author, String startTime, String endTime);
    List<Resource> searchByKeyword(String keyword);
    List<Resource> searchByCategory(String category);
    List<Resource> searchByAuthor(String author);
    List<Resource> searchByTimeRange(String startTime, String endTime);
}
