package top.yuxs.resourcelibrarysystem.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.yuxs.resourcelibrarysystem.DTO.ResourceUpdateDto;
import top.yuxs.resourcelibrarysystem.pojo.Resource;
import top.yuxs.resourcelibrarysystem.pojo.Result;
import top.yuxs.resourcelibrarysystem.service.ResourceService;

import java.util.List;

@RestController
@RequestMapping("/api/resources")
public class ResourceController {
    @Autowired
    private ResourceService resourceService;
    //资源库管理类接口
    //资源库添加接口
    @PostMapping("/admin/add")
    public Result add(@RequestBody Resource resource ){
        String name = (String) StpUtil.getExtra("username");
        if (name==null){
            name="GUEST";
        }
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
//    @PutMapping("/admin/update")
//    public Result update(@RequestBody @Validated(Resource.Update.class) Resource resource){
//        String name = (String) StpUtil.getExtra("username");
//        resourceService.update(resource,name);
//        return Result.success(resource);
//    }
    @PutMapping("/admin/update/{id}")
    public Result update(@PathVariable long id , @RequestBody @Valid ResourceUpdateDto resourceUpdateDto){
        String name = (String) StpUtil.getExtra("username");
        resourceService.update(resourceUpdateDto,name,id);
        return Result.success("更新成功");
    }
    //资源库公开接口
//    @SaCheckLogin
    @GetMapping("/public/get")
    public Result<List<Resource>> list(){
        List<Resource> cs = resourceService.list();
        return Result.success(cs);
    }
//    根据id查询资源接口
    @GetMapping("/public/get/{id}")
    public Result<Resource> selectByID(@PathVariable long id){
        Resource cs = resourceService.selectById(id);
        return Result.success(cs);
    }
    // 综合搜索接口
    @GetMapping("/public/search")
    public Result<List<Resource>> search(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String startTime,
            @RequestParam(required = false) String endTime) {
        List<Resource> resources = resourceService.search(keyword, category, author, startTime, endTime);
        return Result.success(resources);
    }

    // 关键词搜索
    @GetMapping("/public/search/keyword")
    public Result<List<Resource>> searchByKeyword(@RequestParam String keyword) {
        List<Resource> resources = resourceService.searchByKeyword(keyword);
        return Result.success(resources);
    }

    // 分类搜索
    @GetMapping("/public/search/category")
    public Result<List<Resource>> searchByCategory(@RequestParam String category) {
        List<Resource> resources = resourceService.searchByCategory(category);
        return Result.success(resources);
    }

    // 作者搜索
    @GetMapping("/public/search/author")
    public Result<List<Resource>> searchByAuthor(@RequestParam String author) {
        List<Resource> resources = resourceService.searchByAuthor(author);
        return Result.success(resources);
    }

    // 时间范围搜索
    @GetMapping("/public/search/time")
    public Result<List<Resource>> searchByTimeRange(
            @RequestParam(required = false) String startTime,
            @RequestParam(required = false) String endTime) {
        List<Resource> resources = resourceService.searchByTimeRange(startTime, endTime);
        return Result.success(resources);
    }
}
