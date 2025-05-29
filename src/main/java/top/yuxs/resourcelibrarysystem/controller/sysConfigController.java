package top.yuxs.resourcelibrarysystem.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.yuxs.resourcelibrarysystem.DTO.SysConfigUpdateDTO;
import top.yuxs.resourcelibrarysystem.pojo.Result;
import top.yuxs.resourcelibrarysystem.pojo.SysConfig;
import top.yuxs.resourcelibrarysystem.service.SysConfigService;

@RestController
@RequestMapping("/api/resources")
public class sysConfigController {
    @Autowired
    private SysConfigService sysConfigService;
    //添加系统配置
    @PostMapping("/sys/config/add")
    Result<String> addSysConfig(@RequestBody SysConfig sysConfig) {
        long id = sysConfigService.add(sysConfig);
        return Result.success("添加成功！");
    }
    //根据id获取系统配置
    @GetMapping("/sys/config/get/{id}")
    Result<SysConfig> getSysConfig(@PathVariable long id) {
        SysConfig sysConfig = sysConfigService.getById(id);
        return Result.success(sysConfig);
    }
    //根据id修改系统配置
    @PostMapping("/sys/config/update/{id}")
    Result<String> updateSysConfig(@PathVariable long id, @RequestBody SysConfigUpdateDTO sysConfigUpdateDTO) {
        sysConfigService.updateById(id,sysConfigUpdateDTO);
        return Result.success("修改成功");
    }
    //删除系统配置
    @GetMapping("/sys/config/delete")
    Result<String> deleteSysConfig(){
        return Result.success("<UNK>");
    }
}
