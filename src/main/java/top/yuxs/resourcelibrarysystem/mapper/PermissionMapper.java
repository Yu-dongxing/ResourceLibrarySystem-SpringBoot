package top.yuxs.resourcelibrarysystem.mapper;

import org.apache.ibatis.annotations.*;
import top.yuxs.resourcelibrarysystem.pojo.Permission;

@Mapper
public interface PermissionMapper {

    // 插入权限
    @Insert("INSERT INTO permissions (name, description) VALUES (#{name}, #{description})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Permission permission);

    // 根据ID查询权限
    @Select("SELECT * FROM permissions WHERE id = #{id}")
    Permission selectById(Integer id);

    // 根据名称查询权限
    @Select("SELECT * FROM permissions WHERE name = #{name}")
    Permission selectByName(String name);

    // 更新权限信息
    @Update("UPDATE permissions SET name = #{name}, description = #{description} WHERE id = #{id}")
    void update(Permission permission);

    // 删除权限
    @Delete("DELETE FROM permissions WHERE id = #{id}")
    void delete(Integer id);
}
