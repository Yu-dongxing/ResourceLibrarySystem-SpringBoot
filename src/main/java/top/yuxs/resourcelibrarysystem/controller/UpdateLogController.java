package top.yuxs.resourcelibrarysystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.yuxs.resourcelibrarysystem.DTO.UpdateLogDTO;
import top.yuxs.resourcelibrarysystem.pojo.Result;
import top.yuxs.resourcelibrarysystem.pojo.UpdateLog;
import top.yuxs.resourcelibrarysystem.service.UpdateLogService;

import java.util.List;

@RestController
@RequestMapping("/api/resources")
public class UpdateLogController {

    @Autowired
    private UpdateLogService updateLogService;
//    添加更新日志
    @PostMapping("/updatelog/add")
    public Result<String> addUpdateLog(@RequestBody UpdateLog updateLog){
        updateLogService.add(updateLog);
        return Result.success("添加成功！");
    }
//    查询所有更新日志
    @GetMapping("/updatelog/all")
    public Result<List<UpdateLog>> getAll(){
        List<UpdateLog> cs = updateLogService.getAll();
        return Result.success(cs);
    }
//    修改更新日志
    @PutMapping("/updatelog/update/{id}")
    public Result<String> updateLog(@RequestBody UpdateLogDTO updateLog,@PathVariable Integer id){
        updateLogService.updateById(id,updateLog);
        return Result.success("更新成功！！");
    }
//    删除日志
    @GetMapping("/updatelog/delete/{id}")
    public Result<String> deleteById(@PathVariable Integer id){
        updateLogService.deleteById(id);
        return Result.success("删除成功！！");
    }
//    根据id查找某一个日志
    @GetMapping("/updatelog/findlogid/{id}")
    public Result<UpdateLog> findLogById(@PathVariable Integer id){
        UpdateLog cs =  updateLogService.findById(id);
        return Result.success(cs);
    }
}
