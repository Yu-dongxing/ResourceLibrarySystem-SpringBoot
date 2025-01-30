package top.yuxs.resourcelibrarysystem.mapper;

import org.apache.ibatis.annotations.*;
import top.yuxs.resourcelibrarysystem.pojo.Permission;
import top.yuxs.resourcelibrarysystem.pojo.Role;
import top.yuxs.resourcelibrarysystem.pojo.Users;

import java.util.List;
@Mapper
public interface UserMapper {
    // 插入用户
    @Insert("INSERT INTO users (username, password, email, phone_number,  create_time, update_time) " +
            "VALUES (#{username}, #{password}, #{email}, #{phoneNumber}, #{createTime}, #{updateTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Users user);
    // 根据ID查询用户
    @Select("SELECT * FROM users WHERE id = #{id}")
    Users selectById(Long id);
    // 根据手机号查询用户
    @Select("SELECT * FROM users WHERE phone_number = #{phoneNumber}")
    Users selectByPhoneNumber(String phoneNumber);
    // 更新用户信息
    @Update("UPDATE users SET username = #{username}, password = #{password}, email = #{email}, " +
            "phone_number = #{phoneNumber}, is_active = #{isActive}, update_time = #{updateTime} " +
            "WHERE id = #{id}")
    void update(Users user);
    // 删除用户
    @Delete("DELETE FROM users WHERE id = #{id}")
    void delete(Long id);
    // 查询用户的所有角色
    @Select("SELECT r.* FROM roles r JOIN user_roles ur ON r.id = ur.role_id WHERE ur.user_id = #{userId}")
    List<Role> selectRolesByUserId(Long userId);
    // 查询用户的所有权限
    @Select("SELECT p.* FROM permissions p JOIN role_permissions rp ON p.id = rp.permission_id " +
            "JOIN user_roles ur ON rp.role_id = ur.role_id WHERE ur.user_id = #{userId}")
    List<Permission> selectPermissionsByUserId(Long userId);

    @Insert("INSERT INTO user_roles (user_id, role_id) VALUES (#{userId}, #{roleId})")
    void insertUserRole(@Param("userId") Long userId, @Param("roleId") Integer roleId);

    @Delete("DELETE FROM user_roles WHERE user_id = #{userId}")
    void deleteUserRoles(Long userId);
}
