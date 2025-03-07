package top.yuxs.resourcelibrarysystem.service;

import top.yuxs.resourcelibrarysystem.DTO.GetResourceFileListDTO;
import top.yuxs.resourcelibrarysystem.DTO.ResourceFileDTO;
import top.yuxs.resourcelibrarysystem.DTO.ResourceUpdateDto;
import top.yuxs.resourcelibrarysystem.DTO.ResourceCompleteUpdateDTO;
import top.yuxs.resourcelibrarysystem.pojo.Resource;

import java.util.List;

public interface ResourceService {
    void add(Resource resource,String name);

    List<Resource> list();

    void logicDelete(Long id);


    void update(ResourceUpdateDto resourceUpdateDto, String name,Long id);

    List<Resource> search(String keyword, String category, String author, String startTime, String endTime);
    List<Resource> searchByKeyword(String keyword);
    List<Resource> searchByCategory(String category);
    List<Resource> searchByAuthor(String author);
    List<Resource> searchByTimeRange(String startTime, String endTime);

    Resource selectById(long id);

    List<Resource> selectAudit();

    void auditById(Long id);

    void addFileResource(ResourceFileDTO data,String name);

    List<GetResourceFileListDTO> resourceFileList();

    GetResourceFileListDTO selectResourceFileByID(long id);

    void completeUpdate(Long id, ResourceCompleteUpdateDTO updateData, String username);
}
