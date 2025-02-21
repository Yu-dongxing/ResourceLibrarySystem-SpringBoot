package top.yuxs.resourcelibrarysystem.service;

import top.yuxs.resourcelibrarysystem.DTO.SysInfoUpdateDTO;
import top.yuxs.resourcelibrarysystem.pojo.SysInfo;

import java.util.List;

public interface SysInfoService {
    void add(SysInfo sysInfo);

    List<SysInfo> findAll();

    SysInfo findByID(Long id);

    void delentById(Long id);

    void update(SysInfoUpdateDTO sysInfoUpdateDTO, Long id);
}
