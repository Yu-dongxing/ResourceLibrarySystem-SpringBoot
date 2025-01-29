package top.yuxs.resourcelibrarysystem.mapper;
import org.apache.ibatis.annotations.*;
import top.yuxs.resourcelibrarysystem.pojo.Permission;
import top.yuxs.resourcelibrarysystem.pojo.Role;

import java.util.List;
@Mapper
public interface RolePermissionsMapper {

    // 插入角色权限关联
    @Insert("INSERT INTO role_permissions (role_id, permission_id) VALUES (#{roleId}, #{permissionId})")
    void insert(Integer roleId, Integer permissionId);

    // 删除角色权限关联
    @Delete("DELETE FROM role_permissions WHERE role_id = #{roleId} AND permission_id = #{permissionId}")
    void delete(Integer roleId, Integer permissionId);

    // 查询角色的所有权限ID
    @Select("SELECT permission_id FROM role_permissions WHERE role_id = #{roleId}")
    List<Integer> selectPermissionIdsByRoleId(Integer roleId);
}