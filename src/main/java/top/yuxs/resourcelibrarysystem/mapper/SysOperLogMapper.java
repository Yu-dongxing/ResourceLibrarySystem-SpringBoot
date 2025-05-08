package top.yuxs.resourcelibrarysystem.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import top.yuxs.resourcelibrarysystem.pojo.SysOperLog;

import java.util.List;

@Mapper
public interface SysOperLogMapper {
    @Select(" SELECT * FROM sys_oper_log ")
    List<SysOperLog> findAll();
    @Insert("INSERT INTO sys_oper_log" +
            "(title, business_type, `method`, request_method, operator_type, oper_name, oper_user_id, dept_name, oper_url, oper_ip, oper_location, oper_param, json_result, status, error_msg, oper_time)" +
            "VALUES(" +
            "#{title}, " +
            "#{businessType}, " +
            "#{method}, " +
            "#{requestMethod}, " +
            "#{operatorType}, " +
            "#{operName}, " +
            "#{operUserId}, " +
            "#{deptName}, " +
            "#{operUrl}, " +
            "#{operIp}, " +
            "#{operLocation}, " +
            "#{operParam}, " +
            "#{jsonResult}, " +
            "#{status}, " +
            "#{errorMsg}, " +
            "#{operTime}" +
            ")")
    void Add(SysOperLog sysOperLog);
}
