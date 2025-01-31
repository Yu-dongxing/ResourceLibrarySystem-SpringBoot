package top.yuxs.resourcelibrarysystem.config;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.jwt.StpLogicJwtForSimple;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpLogic;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import top.yuxs.resourcelibrarysystem.interceptor.PermissionInterceptor;
import cn.dev33.satoken.router.SaHttpMethod;
import cn.dev33.satoken.context.SaHolder;

@Configuration
public class SaTokenConfigure implements WebMvcConfigurer {
    @Autowired
    private PermissionInterceptor permissionInterceptor;
    
    // Sa-Token 整合 jwt (Simple 简单模式)
    @Bean
    public StpLogic getStpLogicJwt() {
        return new StpLogicJwtForSimple();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册 Sa-Token 拦截器，定义详细认证规则
        registry.addInterceptor(new SaInterceptor(handler -> {
            // 指定需要排除的跨域请求 
            SaRouter.match("/**")
                    .notMatch("/api/resources/sign")        
                    .notMatch("/api/resources/login")
                    .notMatch("/api/resources/public/**")
                    .check(r -> {
                        // 允许跨域的 OPTIONS 请求直接通过 
                        if(SaHolder.getRequest().getMethod().equals(SaHttpMethod.OPTIONS.toString())) {
                            SaRouter.stop();
                        }
                        // 其他请求需要登录认证
                        StpUtil.checkLogin();
                    });
        })).addPathPatterns("/**");
        
        // 注册权限拦截器
//        registry.addInterceptor(permissionInterceptor)
//                .addPathPatterns("/**");
    }
}