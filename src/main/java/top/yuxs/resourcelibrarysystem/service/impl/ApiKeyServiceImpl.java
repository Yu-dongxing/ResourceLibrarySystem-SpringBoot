package top.yuxs.resourcelibrarysystem.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.yuxs.resourcelibrarysystem.mapper.ApiKeyMapper;
import top.yuxs.resourcelibrarysystem.pojo.ApiKey;
import top.yuxs.resourcelibrarysystem.service.ApiKeyService;

import java.util.List;

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
}
