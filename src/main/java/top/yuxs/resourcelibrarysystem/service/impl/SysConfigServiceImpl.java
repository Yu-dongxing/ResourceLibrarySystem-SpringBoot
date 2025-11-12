package top.yuxs.resourcelibrarysystem.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.yuxs.resourcelibrarysystem.DTO.SysConfigUpdateDTO;
import top.yuxs.resourcelibrarysystem.mapper.SysConfigMapper;
import top.yuxs.resourcelibrarysystem.pojo.SysConfig;
import top.yuxs.resourcelibrarysystem.service.SysConfigService;

import java.time.LocalDateTime;

@Service
public class SysConfigServiceImpl implements SysConfigService {
    @Autowired
    private SysConfigMapper sysConfigMapper;

    @Override
    public long add(SysConfig sysConfig) {
        sysConfig.setConfigCreateTime(LocalDateTime.now());
        sysConfig.setConfigUpdateTime(LocalDateTime.now());
        sysConfigMapper.insert(sysConfig);
        return sysConfig.getConfigId();
    }

    @Override
    public SysConfig getById(long id) {
        return sysConfigMapper.selectById(id);
    }

    @Override
    public void updateById(long id, SysConfigUpdateDTO sysConfig) {
        SysConfig sys = getById(id);
        sys.setConfigView(sysConfig.getConfigView());
        sys.setConfigUpdateTime(LocalDateTime.now());
        sysConfigMapper.updateById(sys);
    }
}
