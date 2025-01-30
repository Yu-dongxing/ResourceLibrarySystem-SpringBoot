package top.yuxs.resourcelibrarysystem.interceptor;

import cn.dev33.satoken.stp.StpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import top.yuxs.resourcelibrarysystem.annotation.RequiresPermission;
import top.yuxs.resourcelibrarysystem.service.UserService;
import top.yuxs.resourcelibrarysystem.mapper.UserMapper;
import top.yuxs.resourcelibrarysystem.pojo.Permission;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

@Component
public class PermissionInterceptor implements HandlerInterceptor {
    
    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        RequiresPermission requiresPermission = handlerMethod.getMethodAnnotation(RequiresPermission.class);
        
        if (requiresPermission == null) {
            return true;
        }

        // 获取当前登录用户ID
        long userId = StpUtil.getLoginIdAsLong();
        // 获取用户的所有权限
        List<Permission> permissions = userMapper.selectPermissionsByUserId(userId);
        
        // 检查是否有所需权限
        String requiredPermission = requiresPermission.value();
        boolean hasPermission = permissions.stream()
                .anyMatch(p -> p.getName().equals(requiredPermission));
                
        if (!hasPermission) {
            throw new RuntimeException("没有操作权限");
        }
        
        return true;
    }
} 