package top.yuxs.resourcelibrarysystem.service;

import top.yuxs.resourcelibrarysystem.DTO.PageAccessLogUpdateDTO;
import top.yuxs.resourcelibrarysystem.pojo.PageAccessLog;

import java.util.List;

public interface PageAccessLogService {
    void add(PageAccessLog pageAccessLog);

    List<PageAccessLog> gatAll();

    PageAccessLog getById(long id);

    void deleteById(long id);

//    void updateById(long id, PageAccessLogUpdateDTO pageAccessLogUpdateDTO);
}
