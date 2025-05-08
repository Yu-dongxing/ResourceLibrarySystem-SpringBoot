package top.yuxs.resourcelibrarysystem.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.yuxs.resourcelibrarysystem.mapper.ApiKeyMapper;
import top.yuxs.resourcelibrarysystem.pojo.ApiKey;
import top.yuxs.resourcelibrarysystem.service.ApiKeyService;
import top.yuxs.resourcelibrarysystem.utils.ApiKeyUtil;

import java.util.List;

@Slf4j
@Service
public class ApiKeyServiceImpl implements ApiKeyService {

    @Autowired
    private ApiKeyMapper apiKeyMapper;

    @Override
    public boolean add(ApiKey apiKey) {
        return apiKeyMapper.add(apiKey);
    }

    @Override
    public List<ApiKey> findByUserId(Long userid) {

        return apiKeyMapper.findByUserId(userid);
    }

    @Override
    public List<ApiKey> findByAll() {
        return apiKeyMapper.findAll();
    }

    @Override
    public ApiKey findByKey(String apikey) {
        return apiKeyMapper.findByApiKey(apikey);
    }

    @Override
    public Boolean isKey(String apikey) {
        ApiKey  keyInfo  = findByKey(apikey);
//        if (keyInfo!=null && ApiKeyUtil.isExpired(keyInfo.getCreatedAt(),keyInfo.getExpiresAt())){
//            return true;
//        }else {
//            return false;
//        }
        if(keyInfo!=null){
            return ApiKeyUtil.isExpired(keyInfo.getCreatedAt(),keyInfo.getExpiresAt());
        }else {
            log.error("找不到key");
            return true;
        }
    }
}
