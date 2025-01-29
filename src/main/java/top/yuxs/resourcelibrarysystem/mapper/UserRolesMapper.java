package top.yuxs.resourcelibrarysystem.mapper;
import org.apache.ibatis.annotations.*;

import java.util.List;
@Mapper
public interface UserRolesMapper {
    // 插入用户角色关联
    @Insert("INSERT INTO user_roles (user_id, role_id) VALUES (#{userId}, #{roleId})")
    void insert(Long userId, Integer roleId);
    // 删除用户角色关联
    @Delete("DELETE FROM user_roles WHERE user_id = #{userId} AND role_id = #{roleId}")
    void delete(Long userId, Integer roleId);
    // 查询用户的所有角色
    @Select("SELECT role_id FROM user_roles WHERE user_id = #{userId}")
    List<Integer> selectRoleIdsByUserId(Long userId);
}
