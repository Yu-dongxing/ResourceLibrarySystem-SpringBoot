package top.yuxs.resourcelibrarysystem.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.yuxs.resourcelibrarysystem.pojo.Resource;
import top.yuxs.resourcelibrarysystem.pojo.Result;
import top.yuxs.resourcelibrarysystem.service.ResourceService;

import java.util.List;

@RestController
@RequestMapping("/api/resources")
@CrossOrigin // 允许跨域请求
public class ResourceController {
    @Autowired
    private ResourceService resourceService;
    //资源库管理类接口
    //资源库添加接口
    @PostMapping("/admin/add")
    public Result add(@RequestBody Resource resource ){
        String name = (String) StpUtil.getExtra("username");
        resourceService.add(resource,name);
        return Result.success(resource);
    }
    //资源库逻辑删除接口
    @GetMapping("/admin/delete/{id}")
    public Result logicDelete(@PathVariable Long id) {
        resourceService.logicDelete(id);
        return Result.success("删除成功");
    }
    //资源库更新接口
    @PutMapping("/admin/update")
    public Result update(@RequestBody @Validated(Resource.Update.class) Resource resource){
        String name = (String) StpUtil.getExtra("username");
        resourceService.update(resource,name);
        return Result.success(resource);
    }
    //资源库公开接口
//    @SaCheckLogin
    @GetMapping("/public/get")
    public Result<List<Resource>> list(){
        List<Resource> cs = resourceService.list();
        return Result.success(cs);
    }
}
