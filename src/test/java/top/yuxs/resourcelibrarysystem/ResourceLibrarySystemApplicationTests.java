package top.yuxs.resourcelibrarysystem;

import cn.dev33.satoken.stp.SaLoginConfig;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ResourceLibrarySystemApplicationTests {
    @Value("${File-Proxy-Website.url}")
    private String url;
    @Test
    public void login() {
//        StpUtil.login(10001);
//        StpUtil.login(10001, SaLoginConfig
//                .setExtra("name", "zhangsan")
//                .setExtra("age", 18)
//                .setExtra("role", "超级管理员"));
//        // 获取扩展参数
//        String name = (String) StpUtil.getExtra("name");
//        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
//        System.out.println(tokenInfo.tokenValue);
        System.out.println(url);
    }

}
