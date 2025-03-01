package top.yuxs.resourcelibrarysystem.controller;

import cn.dev33.satoken.stp.StpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.yuxs.resourcelibrarysystem.pojo.ApiKey;
import top.yuxs.resourcelibrarysystem.pojo.Result;
import top.yuxs.resourcelibrarysystem.service.ApiKeyService;
import top.yuxs.resourcelibrarysystem.utils.ApiKeyUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/resources")
public class ApiKeyController {
    @Autowired
    private ApiKeyService apiKeyService;
    @PostMapping("/apikey/add")
    public Result<String> PostApiKey(@RequestBody ApiKey apiKey) throws ParseException {
        String apikey = UUID.randomUUID().toString().replaceAll("-", "");

        // 获取当前时间并格式化
        LocalDateTime now = LocalDateTime.now();
        String formattedNow = now.format(ApiKeyUtil.formatter);

        // 计算过期时间
        String exTime = ApiKeyUtil.calculateExpirationTime(formattedNow);

        // 设置 API Key 的属性
        apiKey.setApiKey(apikey);

        apiKey.setCreatedAt(LocalDateTime.parse(formattedNow, ApiKeyUtil.formatter)); // 使用 formatter 解析

        apiKey.setExpiresAt(LocalDateTime.parse(exTime, ApiKeyUtil.formatter));       // 使用 formatter 解析

        apiKey.setStatus(1);

        apiKey.setUserId(StpUtil.getLoginIdAsLong());

        if (apiKeyService.add(apiKey)) {
//            List<ApiKey> cs = apiKeyService.findByUserId(StpUtil.getLoginIdAsLong());
//            if(cs!=null){
//                return Result.success(cs);
//            }else {
//                return Result.error("获取apiKey失败！！！");
//            }
            return Result.success("添加apiKey成功！！");
        } else {
            return Result.error("添加apiKey失败！！！");
        }
    }
    @GetMapping("/apikey/get/user")
    public Result<List<ApiKey>> getApiKeyByUserId(){
        List<ApiKey> cs = apiKeyService.findByUserId(StpUtil.getLoginIdAsLong());
        return Result.success(cs);
    }
    @GetMapping("/apikey/get/all")
    public Result<List<ApiKey>> getApiKeyAll(){
        List<ApiKey> cs = apiKeyService.findByAll();
        return Result.success(cs);
    }
//    @GetMapping("/apikey/get/es/{key}")
//    public Result<String> getApiKeyOrUse(@PathVariable String  key){
//
//        return Result.success("true");
//    }
}
