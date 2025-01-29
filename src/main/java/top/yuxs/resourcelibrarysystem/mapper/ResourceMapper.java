package top.yuxs.resourcelibrarysystem.mapper;

import org.apache.ibatis.annotations.*;
import top.yuxs.resourcelibrarysystem.pojo.Resource;
import java.util.List;

@Mapper
public interface ResourceMapper {
    
    @Insert("INSERT INTO resource_list (name, url, create_time, author, tab, img,update_time) " +
            "VALUES (#{name}, #{url}, #{createTime}, #{author}, #{tab}, #{img},#{updateTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Resource resource);
    
    @Select("SELECT * FROM resource_list WHERE id = #{id}")
    Resource findById(Long id);
    
    @Select("SELECT * FROM resource_list WHERE is_deleted = 0")
    List<Resource> findAll();
    
    @Select("SELECT * FROM resource_list WHERE tab = #{tab}")
    List<Resource> findByTab(String tab);
    
    @Select("SELECT * FROM resource_list WHERE name LIKE CONCAT('%', #{keyword}, '%')")
    List<Resource> search(String keyword);
    
    @Update("UPDATE resource_list SET name = #{name}, url = #{url}, " +
            "update_time=#{update_time}, author = #{author}, " +
            "tab = #{tab}, img = #{img} WHERE id = #{id} AND is_deleted = 0 ")
    void update(Resource resource);
    
    @Delete("DELETE FROM resource_list WHERE id = #{id}")
    void deleteById(Long id);
    // 逻辑删除
    @Update("UPDATE resource_list SET is_deleted = 1 WHERE id = #{id}")
    void logicDeleteById(Long id);
}