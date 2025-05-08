package top.yuxs.resourcelibrarysystem.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import top.yuxs.resourcelibrarysystem.pojo.UserLoginLog;

import java.util.List;

@Mapper
public interface UserLoginLogMapper {
    @Select("SELECT * FROM user_login_log ")
    List<UserLoginLog> findAll();

    @Insert("INSERT INTO user_login_log" +
            "(login_user_id, login_time, login_ip, login_user_access_log_id)" +
            "VALUES(#{loginUserId}, #{loginTime}, #{loginIp}, #{loginUserAccessLogId})")
    void add(UserLoginLog userLoginLog);

    @Select("SELECT * FROM user_login_log WHERE login_user_id = #{userId} ORDER BY login_time DESC")
    List<UserLoginLog> findByUserId(Long userId);
}
