package top.yuxs.resourcelibrarysystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.yuxs.resourcelibrarysystem.DTO.SysInfoUpdateDTO;
import top.yuxs.resourcelibrarysystem.pojo.Result;
import top.yuxs.resourcelibrarysystem.pojo.SysInfo;
import top.yuxs.resourcelibrarysystem.service.SysInfoService;

import java.util.List;

@RestController
@RequestMapping("/api/resources")
public class SysInfoController {
    @Autowired
    private SysInfoService sysInfoService;
//    添加系统信息
    @PostMapping("/sysinfo/add")
    public Result<String> addSysInfo(@RequestBody SysInfo sysInfo){
        sysInfoService.add(sysInfo);
        return Result.success("添加成功！");
    }
//    查询所有系统信息
    @GetMapping("/public/sysinfo/all")
    public Result<List<SysInfo>> gatAll(){
        List<SysInfo> cs = sysInfoService.findAll();
        return Result.success(cs);
    }
//    根据id获取系统信息
    @GetMapping("/public/sysinfo/findbuid/{id}")
    public Result<SysInfo> findById(@PathVariable Long id){
        SysInfo cs = sysInfoService.findByID(id);
        return Result.success(cs);
    }
//    根据id删除系统信息
    @GetMapping("/sysinfo/delent/{id}")
    public Result<String> delentById(@PathVariable Long id) {
        sysInfoService.delentById(id);
        return Result.success("删除成功！！");
    }
//    更新系统信息
    @PostMapping("/sysinfo/update/{id}")
    public Result<String> updateById(@RequestBody SysInfoUpdateDTO sysInfoUpdateDTO,@PathVariable Long id){
        sysInfoService.update(sysInfoUpdateDTO,id);
        return Result.success("更新成功！");
    }
}
