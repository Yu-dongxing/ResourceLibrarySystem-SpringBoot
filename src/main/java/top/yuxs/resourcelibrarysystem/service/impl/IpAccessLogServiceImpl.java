package top.yuxs.resourcelibrarysystem.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.yuxs.resourcelibrarysystem.mapper.IpAccessLogMapper;
import top.yuxs.resourcelibrarysystem.pojo.IpAccessLog;
import top.yuxs.resourcelibrarysystem.service.IpAccessLogService;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class IpAccessLogServiceImpl  implements IpAccessLogService{
    @Autowired
    private IpAccessLogMapper ipAccessLogMapper;
    @Override
    public void add(IpAccessLog ipAccessLog) {
        IpAccessLog ip = ipAccessLogMapper.selectByIpAddress(ipAccessLog.getIpAddress());
        if(ip!=null){
            ip.setIpAccessTime(LocalDateTime.now());
            ip.setIpRepeat(ip.getIpRepeat()+1);
            ip.setIpUserAgent(ipAccessLog.getIpUserAgent());
            ip.setIpUserDevice(ipAccessLog.getIpUserDevice());
            ip.setIpCity(ipAccessLog.getIpCity());
            ip.setIpProvince(ipAccessLog.getIpProvince());
            ipAccessLogMapper.updateById(ip);
        }else {
            ipAccessLog.setIpRepeat(1L);
            ipAccessLog.setIpAccessTime(LocalDateTime.now());
            ipAccessLogMapper.insertIpAccessLog(ipAccessLog);
        }
    }
    @Override
    public List<IpAccessLog> gatAll() {
        return ipAccessLogMapper.selectAll();
    }

    @Override
    public IpAccessLog getByIpAddress(String ip) {
        return ipAccessLogMapper.selectByIpAddress(ip);
    }

    @Override
    public IPage<IpAccessLog> getPage(Integer pageNum, Integer pageSize) {
        // 创建分页对象
        IPage<IpAccessLog> page = new Page<>(pageNum, pageSize);
        // 执行分页查询
        IPage<IpAccessLog> ipAccessLogPage = ipAccessLogMapper.selectPage(page, null);
        return ipAccessLogPage;
    }


}
