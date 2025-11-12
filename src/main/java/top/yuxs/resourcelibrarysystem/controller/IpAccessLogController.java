package top.yuxs.resourcelibrarysystem.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.yuxs.resourcelibrarysystem.pojo.IpAccessLog;
import top.yuxs.resourcelibrarysystem.pojo.Result;
import top.yuxs.resourcelibrarysystem.service.IpAccessLogService;
import top.yuxs.resourcelibrarysystem.utils.SysConfigUtil;

import java.util.List;
@Log4j2
@RestController
@RequestMapping("/api/resources")
public class IpAccessLogController {
    @Autowired
    private IpAccessLogService ipAccessLogService;
    @Autowired
    private SysConfigUtil sysConfigUtil;
    //    添加日志
    @PostMapping("/public/ip_log/add")
    public Result<String> addLog(@RequestBody @Valid IpAccessLog ipAccessLog) {
        String cs = sysConfigUtil.getSysConfigById("isIpLog");
        if(cs.equals("true")) {
            System.out.println("允许添加日志");
            ipAccessLogService.add(ipAccessLog);
            return Result.success("添加成功");
        }else {
            System.out.println("不允许添加日志");
            return Result.success("系统已不允许添加日志！");
        }
    }
    //    查询所有日志
    @GetMapping("/public/ip_log/all")
    public Result<List<IpAccessLog>> gatAll(){
        List<IpAccessLog> cs = ipAccessLogService.gatAll();
        return Result.success(cs);
    }
    //分页查询
    @GetMapping("/public/ip_log/page")
    public Result<IPage<IpAccessLog>> getPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        IPage<IpAccessLog> ipAccessLogPage = ipAccessLogService.getPage(pageNum, pageSize);
        return Result.success(ipAccessLogPage);
    }
//    根据ip地址查询日志
    @GetMapping("/public/ip_log/IpAddress/{ip}")
    public Result<IpAccessLog> getByIpAddress(@PathVariable String ip){
        IpAccessLog ipAccessLog = ipAccessLogService.getByIpAddress(ip);
        return Result.success(ipAccessLog);
    }
}
