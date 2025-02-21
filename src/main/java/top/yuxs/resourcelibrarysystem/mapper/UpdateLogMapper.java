package top.yuxs.resourcelibrarysystem.mapper;

import org.apache.ibatis.annotations.*;
import top.yuxs.resourcelibrarysystem.pojo.UpdateLog;

import java.util.List;

@Mapper
public interface UpdateLogMapper {
    // 查询所有日志
    @Select("SELECT * FROM update_log ORDER BY log_creat_time DESC")
    List<UpdateLog> findAll();

    // 根据ID查询日志
    @Select("SELECT * FROM update_log WHERE log_id = #{logId}")
    UpdateLog findById(@Param("logId") Integer logId);

    // 插入日志
    @Insert("INSERT INTO update_log (log_creat_time, log_title, `desc`, type, hollow) VALUES (#{logCreatTime}, #{logTitle}, #{desc}, #{type}, #{hollow})")
    int insert(UpdateLog updateLog);

    // 更新日志
    @Update("UPDATE update_log SET log_creat_time = #{logCreatTime}, log_title = #{logTitle}, `desc` = #{desc}, type = #{type}, hollow = #{hollow} WHERE log_id = #{logId}")
    int update(UpdateLog updateLog);

    // 删除日志
    @Delete("DELETE FROM update_log WHERE log_id = #{logId}")
    void deleteById(@Param("logId") Integer logId);
}
