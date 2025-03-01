package top.yuxs.resourcelibrarysystem.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import top.yuxs.resourcelibrarysystem.pojo.ApiKey;

import java.util.List;

@Mapper
public interface ApiKeyMapper {
    @Insert("INSERT INTO api_key" +
            "(api_key, created_at, expires_at, status, user_id, app_id, description)" +
            "VALUES(#{apiKey}, #{createdAt}, #{expiresAt}, #{status}, #{userId}, #{appId}, #{description});")
    boolean add(ApiKey apiKey);

    @Select("SELECT * FROM api_key;")
    List<ApiKey> findAll();

    @Select("SELECT * FROM api_key WHERE id = #{id};")
    ApiKey findById(Long id);

    @Select("SELECT * FROM api_key WHERE user_id = #{userId};")
    List<ApiKey> findByUserId(Long userId);
    @Select("SELECT * FROM api_key WHERE app_id = #{appId};")
    List<ApiKey> findAppId(Long appId);

    @Delete("DELETE FROM api_key WHERE id=#{id};")
    void deleteById(Long id);

    @Select("SELECT * FROM api_key WHERE api_key = #{apiKey};")
    ApiKey findByApiKey(String apiKey);

}
