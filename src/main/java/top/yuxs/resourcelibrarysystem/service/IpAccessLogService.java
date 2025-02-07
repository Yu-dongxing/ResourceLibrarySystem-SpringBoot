package top.yuxs.resourcelibrarysystem.service;

import top.yuxs.resourcelibrarysystem.pojo.IpAccessLog;
import top.yuxs.resourcelibrarysystem.pojo.PageAccessLog;

import java.util.List;

public interface IpAccessLogService {
    void add(IpAccessLog ipAccessLog);

    List<IpAccessLog> gatAll();

    IpAccessLog getByIpAddress(String ip);
}
