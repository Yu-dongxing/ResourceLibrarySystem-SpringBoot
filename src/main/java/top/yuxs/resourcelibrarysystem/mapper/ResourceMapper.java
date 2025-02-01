package top.yuxs.resourcelibrarysystem.mapper;

import org.apache.ibatis.annotations.*;
import top.yuxs.resourcelibrarysystem.pojo.Resource;
import java.util.List;

@Mapper
public interface ResourceMapper {
    
    @Insert("INSERT INTO resource_list (name, url, create_time, author, tab, img, update_time) " +
            "VALUES (#{name}, #{url}, #{createTime}, #{author}, #{tab}, #{img}, #{updateTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Resource resource);
    
    @Select("SELECT * FROM resource_list WHERE id = #{id} AND is_deleted = 0")
    Resource findById(Long id);
    
    @Select("SELECT * FROM resource_list WHERE is_deleted = 0 ORDER BY create_time DESC")
    List<Resource> findAll();
    
    @Select("SELECT * FROM resource_list WHERE tab = #{tab} AND is_deleted = 0 ORDER BY create_time DESC")
    List<Resource> findByTab(String tab);
    
    @Select("SELECT * FROM resource_list WHERE is_deleted = 0 " +
            "AND (#{keyword} IS NULL OR name LIKE CONCAT('%', #{keyword}, '%')) " +
            "AND (#{category} IS NULL OR tab = #{category}) " +
            "AND (#{author} IS NULL OR author = #{author}) " +
            "AND (#{startTime} IS NULL OR create_time >= #{startTime}) " +
            "AND (#{endTime} IS NULL OR create_time <= #{endTime}) " +
            "ORDER BY create_time DESC")
    List<Resource> search(@Param("keyword") String keyword,
                         @Param("category") String category,
                         @Param("author") String author,
                         @Param("startTime") String startTime,
                         @Param("endTime") String endTime);

    @Select("SELECT * FROM resource_list WHERE is_deleted = 0 " +
            "AND name LIKE CONCAT('%', #{keyword}, '%') " +
            "ORDER BY create_time DESC")
    List<Resource> searchByKeyword(@Param("keyword") String keyword);

    @Select("SELECT * FROM resource_list WHERE is_deleted = 0 " +
            "AND tab = #{category} " +
            "ORDER BY create_time DESC")
    List<Resource> searchByCategory(@Param("category") String category);

    @Select("SELECT * FROM resource_list WHERE is_deleted = 0 " +
            "AND author = #{author} " +
            "ORDER BY create_time DESC")
    List<Resource> searchByAuthor(@Param("author") String author);

    @Select("SELECT * FROM resource_list WHERE is_deleted = 0 " +
            "AND (#{startTime} IS NULL OR create_time >= #{startTime}) " +
            "AND (#{endTime} IS NULL OR create_time <= #{endTime}) " +
            "ORDER BY create_time DESC")
    List<Resource> searchByTimeRange(@Param("startTime") String startTime, 
                                   @Param("endTime") String endTime);

    @Update("UPDATE resource_list SET name = #{name}, url = #{url}, " +
            "update_time = #{updateTime}, author = #{author}, " +
            "tab = #{tab}, img = #{img} WHERE id = #{id} AND is_deleted = 0")
    void update(Resource resource);
    
    @Update("UPDATE resource_list SET is_deleted = 1 WHERE id = #{id}")
    void logicDeleteById(Long id);
}