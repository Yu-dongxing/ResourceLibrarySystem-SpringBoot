package top.yuxs.resourcelibrarysystem.service;

import top.yuxs.resourcelibrarysystem.pojo.ApiKey;

import java.util.List;

public interface ApiKeyService {
    boolean add(ApiKey apiKey);

    List<ApiKey> findByUserId(Long userid);

    List<ApiKey> findByAll();

    ApiKey findByKey(String apikey);

    Boolean isKey(String apikey);
}
