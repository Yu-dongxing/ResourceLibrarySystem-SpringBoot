package top.yuxs.resourcelibrarysystem.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import top.yuxs.resourcelibrarysystem.pojo.IpAccessLog;

import java.util.List;

public interface IpAccessLogService {
    void add(IpAccessLog ipAccessLog);

    List<IpAccessLog> gatAll();

    IpAccessLog getByIpAddress(String ip);

    IPage<IpAccessLog> getPage(Integer pageNum, Integer pageSize);
}
