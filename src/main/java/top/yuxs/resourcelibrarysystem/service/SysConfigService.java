package top.yuxs.resourcelibrarysystem.service;

import top.yuxs.resourcelibrarysystem.DTO.SysConfigUpdateDTO;
import top.yuxs.resourcelibrarysystem.pojo.SysConfig;

public interface SysConfigService {
    long add(SysConfig sysConfig);

    SysConfig getById(long id);

    void updateById(long id, SysConfigUpdateDTO sysConfig);
}
