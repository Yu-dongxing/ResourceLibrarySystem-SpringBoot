package top.yuxs.resourcelibrarysystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.yuxs.resourcelibrarysystem.pojo.Result;
import top.yuxs.resourcelibrarysystem.pojo.Role;
import top.yuxs.resourcelibrarysystem.service.RoleService;

import java.util.List;

@RestController
@RequestMapping("/api/resources")
@CrossOrigin // 允许跨域请求
public class RoleController {
    @Autowired
    private RoleService roleService;
    @GetMapping("/public/getRoles")
    public Result<List<Role>> getRoleAll(){
        List<Role> li = roleService.getRoleAll();
        return Result.success(li);
    }
}
