package top.yuxs.resourcelibrarysystem.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.yuxs.resourcelibrarysystem.mapper.SysOperLogMapper;
import top.yuxs.resourcelibrarysystem.pojo.SysOperLog;
import top.yuxs.resourcelibrarysystem.service.SysOperLogService;

@Service
@Slf4j
public class SysOperLogServiceImpl implements SysOperLogService {
    @Autowired
    private SysOperLogMapper sysOperLogMapper;

    @Override
    public void add(SysOperLog syslog) {
        sysOperLogMapper.Add(syslog);
    }
}
