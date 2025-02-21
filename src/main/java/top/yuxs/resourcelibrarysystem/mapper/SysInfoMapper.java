package top.yuxs.resourcelibrarysystem.mapper;

import org.apache.ibatis.annotations.*;
import top.yuxs.resourcelibrarysystem.pojo.SysInfo;

import java.util.List;

@Mapper
public interface SysInfoMapper {
    // 查询所有系统信息
    @Select("SELECT * FROM sys_info")
    List<SysInfo> findAll();

    // 根据 ID 查询系统信息
    @Select("SELECT * FROM sys_info WHERE info_id = #{infoId}")
    SysInfo findById(@Param("infoId") Long infoId);

    // 插入系统信息
    @Insert("INSERT INTO sys_info (info_create_time, info_update_time, info_title, info_p, info_view, info_desc, info_type) " +
            "VALUES (#{infoCreateTime}, #{infoUpdateTime}, #{infoTitle}, #{infoP}, #{infoView}, #{infoDesc}, #{infoType})")
    @Options(useGeneratedKeys = true, keyProperty = "infoId")
    int insert(SysInfo sysInfo);

    // 更新系统信息
    @Update("UPDATE sys_info SET " +
            "info_create_time = #{infoCreateTime}, " +
            "info_update_time = #{infoUpdateTime}, " +
            "info_title = #{infoTitle}, " +
            "info_p = #{infoP}, " +
            "info_view = #{infoView}, " +
            "info_desc = #{infoDesc}, " +
            "info_type = #{infoType} " +
            "WHERE info_id = #{infoId}")
    int update(SysInfo sysInfo);

    // 删除系统信息
    @Delete("DELETE FROM sys_info WHERE info_id = #{infoId}")
    int deleteById(@Param("infoId") Long infoId);
}
