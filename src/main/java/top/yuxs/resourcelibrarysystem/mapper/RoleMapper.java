package top.yuxs.resourcelibrarysystem.mapper;

import org.apache.ibatis.annotations.*;
import top.yuxs.resourcelibrarysystem.pojo.Permission;
import top.yuxs.resourcelibrarysystem.pojo.Role;

import java.util.List;
@Mapper
public interface RoleMapper {

    // 插入角色
    @Insert("INSERT INTO roles (name, description) VALUES (#{name}, #{description})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Role role);

    // 根据ID查询角色
    @Select("SELECT * FROM roles WHERE id = #{id}")
    Role selectById(Integer id);
    // 查询所有角色
    @Select("SELECT * FROM roles ")
    List<Role> selectByAll();

    // 根据名称查询角色
    @Select("SELECT * FROM roles WHERE name = #{name}")
    Role selectByName(String name);

    // 更新角色信息
    @Update("UPDATE roles SET name = #{name}, description = #{description} WHERE id = #{id}")
    void update(Role role);

    // 删除角色
    @Delete("DELETE FROM roles WHERE id = #{id}")
    void delete(Integer id);

    // 查询角色的所有权限
    @Select("SELECT p.* FROM permissions p JOIN role_permissions rp ON p.id = rp.permission_id " +
            "WHERE rp.role_id = #{roleId}")
    List<Permission> selectPermissionsByRoleId(Integer roleId);
}