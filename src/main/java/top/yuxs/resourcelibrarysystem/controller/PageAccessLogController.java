package top.yuxs.resourcelibrarysystem.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.yuxs.resourcelibrarysystem.pojo.PageAccessLog;
import top.yuxs.resourcelibrarysystem.pojo.Result;
import top.yuxs.resourcelibrarysystem.service.PageAccessLogService;
import top.yuxs.resourcelibrarysystem.utils.SysConfigUtil;

import java.util.List;

@RestController
@RequestMapping("/api/resources")
public class PageAccessLogController {
    @Autowired
    private PageAccessLogService pageAccessLogService;
    @Autowired
    private SysConfigUtil sysConfigUtil;
//    添加日志
    @PostMapping("/public/log/add")
    public Result<String> addLog(@RequestBody @Valid PageAccessLog pageAccessLog) {

        String cs = sysConfigUtil.getSysConfigById("isPageLog");
        if(cs.equals("true")){
            pageAccessLogService.add(pageAccessLog);
            return Result.success("添加成功");
        }else {
            return Result.success("已关闭页面日志");
        }

    }
//    查询所有日志
    @GetMapping("/public/log/all")
    public Result<List<PageAccessLog>> gatAll(){
        List<PageAccessLog> cs = pageAccessLogService.gatAll();
        return Result.success(cs);
    }
//    根据id获取日志信息
    @GetMapping("/public/log/getid/{id}")
    public Result<PageAccessLog> getById(@PathVariable long id){
        PageAccessLog cs = pageAccessLogService.getById(id);
        return Result.success(cs);
    }
//    根据id删除日志信息
    @GetMapping("/public/log/delete/{id}")
    public Result<String> deleteById(@PathVariable long id){
        pageAccessLogService.deleteById(id);
        return Result.success("删除成功");
    }
//    根据id修改日志
//    @GetMapping("/public/log/update/{id}")
//    public Result<String> updateById(@PathVariable long id,@RequestBody PageAccessLogUpdateDTO pageAccessLogUpdateDTO){
//        pageAccessLogService.updateById(id,pageAccessLogUpdateDTO);
//        return Result.success("更新成功");
//    }
}
