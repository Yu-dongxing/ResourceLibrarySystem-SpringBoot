package top.yuxs.resourcelibrarysystem.utils;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.yuxs.resourcelibrarysystem.pojo.SysConfig;
import top.yuxs.resourcelibrarysystem.service.SysConfigService;
//{"isSysLog":false,"isIpLog":true,"isUserLoginLog":false,"isPageLog":true}
@Component
public class SysConfigUtil {
//    @Value("${Sys-Config.id}")
//    private long dataId;
    @Autowired
    private SysConfigService sysConfigService;
    //根据id获取系统配置数据
    public String getSysConfigById(String p){
        SysConfig sysConfig = sysConfigService.getById(34);
        if(sysConfig != null){
            String view = sysConfig.getConfigView();
            JSONObject jsonObject = JSONUtil.parseObj(view);
            return jsonObject.get(p).toString();
        }
        return null;
    }
}
