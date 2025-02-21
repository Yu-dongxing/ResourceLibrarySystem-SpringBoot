package top.yuxs.resourcelibrarysystem.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.yuxs.resourcelibrarysystem.DTO.SysInfoUpdateDTO;
import top.yuxs.resourcelibrarysystem.mapper.SysInfoMapper;
import top.yuxs.resourcelibrarysystem.pojo.SysInfo;
import top.yuxs.resourcelibrarysystem.service.SysInfoService;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SysInfoServiceImpl implements SysInfoService {
    @Autowired
    private SysInfoMapper sysInfoMapper;

    @Override
    public void add(SysInfo sysInfo) {
        sysInfo.setInfoCreateTime(LocalDateTime.now());
        sysInfo.setInfoUpdateTime(LocalDateTime.now());
        sysInfoMapper.insert(sysInfo);
    }

    @Override
    public List<SysInfo> findAll() {
        return sysInfoMapper.findAll();
    }

    @Override
    public SysInfo findByID(Long id) {
        return sysInfoMapper.findById(id);
    }

    @Override
    public void delentById(Long id) {
        sysInfoMapper.deleteById(id);
    }

    @Override
    public void update(SysInfoUpdateDTO sysInfoUpdateDTO,Long id) {
        SysInfo in = findByID(id);
        in.setInfoDesc(sysInfoUpdateDTO.getInfoDesc());
        in.setInfoP(sysInfoUpdateDTO.getInfoP());
        in.setInfoTitle(sysInfoUpdateDTO.getInfoTitle());
        in.setInfoType(sysInfoUpdateDTO.getInfoType());
        in.setInfoView(sysInfoUpdateDTO.getInfoView());
        in.setInfoUpdateTime(LocalDateTime.now());

        sysInfoMapper.update(in);
//        sysInfoMapper.update(sysInfo);
    }
}
