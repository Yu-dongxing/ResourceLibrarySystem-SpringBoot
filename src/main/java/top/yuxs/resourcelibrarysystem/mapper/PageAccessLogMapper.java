package top.yuxs.resourcelibrarysystem.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.yuxs.resourcelibrarysystem.pojo.PageAccessLog;

import java.util.List;
@Mapper
public interface PageAccessLogMapper {
    // 增
    void insertPageAccessLog(PageAccessLog log);

    // 删
    int deletePageAccessLogById(@Param("id") Long id);

    // 改
    int updatePageAccessLog(PageAccessLog log);

    // 查
    PageAccessLog selectPageAccessLogById(@Param("id") Long id);

    // 查询所有
    List<PageAccessLog> selectAllPageAccessLogs();
}
