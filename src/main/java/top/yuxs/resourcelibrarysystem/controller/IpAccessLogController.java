package top.yuxs.resourcelibrarysystem.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.yuxs.resourcelibrarysystem.pojo.IpAccessLog;
import top.yuxs.resourcelibrarysystem.pojo.Result;
import top.yuxs.resourcelibrarysystem.service.IpAccessLogService;

import java.util.List;

@RestController
@RequestMapping("/api/resources")
public class IpAccessLogController {
    @Autowired
    private IpAccessLogService ipAccessLogService;
    //    添加日志
    @PostMapping("/public/ip_log/add")
    public Result<String> addLog(@RequestBody @Valid IpAccessLog ipAccessLog) {
        ipAccessLogService.add(ipAccessLog);
        return Result.success("添加成功");
    }
    //    查询所有日志
    @GetMapping("/public/ip_log/all")
    public Result<List<IpAccessLog>> gatAll(){
        List<IpAccessLog> cs = ipAccessLogService.gatAll();
        return Result.success(cs);
    }
//    根据ip地址查询日志
    @GetMapping("/public/ip_log/IpAddress/{ip}")
    public Result<IpAccessLog> getByIpAddress(@PathVariable String ip){

        IpAccessLog ipAccessLog = ipAccessLogService.getByIpAddress(ip);
        return Result.success(ipAccessLog);
    }
}
