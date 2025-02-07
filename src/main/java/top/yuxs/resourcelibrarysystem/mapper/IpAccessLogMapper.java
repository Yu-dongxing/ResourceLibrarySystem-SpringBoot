package top.yuxs.resourcelibrarysystem.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.yuxs.resourcelibrarysystem.pojo.IpAccessLog;

import java.util.List;

@Mapper
public interface IpAccessLogMapper {
    // 插入页面访问日志
    void insertIpAccessLog(IpAccessLog ipAccessLog);

    // 根据ID删除日志
    void deleteById(Long id);

    // 根据ID更新日志
    void updateById(IpAccessLog ipAccessLog);

    // 根据ID查询日志
    IpAccessLog selectById(Long id);

    // 查询所有日志
    List<IpAccessLog> selectAll();

    // 更新ipRepeat字段
    void incrementIpRepeat(String ipAddress);
//    根据ip地址查询日志
    IpAccessLog selectByIpAddress(String ipAddress);

}
