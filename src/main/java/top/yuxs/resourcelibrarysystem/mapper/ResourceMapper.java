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
    
    @Select("SELECT * FROM resource_list WHERE id = #{id}")
    Resource findById(Long id);
    
    @Select("SELECT * FROM resource_list WHERE is_deleted = 0")
    List<Resource> findAll();
    
    @Select("SELECT * FROM resource_list WHERE tab = #{tab} AND is_deleted = 0")
    List<Resource> findByTab(String tab);
    
    @Select("SELECT * FROM resource_list WHERE name LIKE CONCAT('%', #{keyword}, '%') AND is_deleted = 0")
    List<Resource> search(String keyword);
    
    @Update("UPDATE resource_list SET name = #{name}, url = #{url}, " +
            "update_time = #{updateTime}, author = #{author}, " +
            "tab = #{tab}, img = #{img} WHERE id = #{id} AND is_deleted = 0")
    void update(Resource resource);
    
    @Delete("DELETE FROM resource_list WHERE id = #{id}")
    void deleteById(Long id);
    
    @Update("UPDATE resource_list SET is_deleted = 1 WHERE id = #{id}")
    void logicDeleteById(Long id);

    @Select("<script>" +
            "SELECT * FROM resource_list WHERE is_deleted = 0" +
            "<if test='keyword != null and keyword != \"\"'>" +
            " AND (name LIKE CONCAT('%', #{keyword}, '%') OR url LIKE CONCAT('%', #{keyword}, '%'))" +
            "</if>" +
            "<if test='category != null and category != \"\"'>" +
            " AND tab = #{category}" +
            "</if>" +
            "<if test='author != null and author != \"\"'>" +
            " AND author = #{author}" +
            "</if>" +
            "<if test='startTime != null and startTime != \"\"'>" +
            " AND create_time &gt;= #{startTime}" +
            "</if>" +
            "<if test='endTime != null and endTime != \"\"'>" +
            " AND create_time &lt;= #{endTime}" +
            "</if>" +
            " ORDER BY create_time DESC" +
            "</script>")
    List<Resource> search(@Param("keyword") String keyword,
                         @Param("category") String category,
                         @Param("author") String author,
                         @Param("startTime") String startTime,
                         @Param("endTime") String endTime);

    @Select("SELECT * FROM resource_list WHERE is_deleted = 0 AND (name LIKE CONCAT('%', #{keyword}, '%') OR url LIKE CONCAT('%', #{keyword}, '%')) ORDER BY create_time DESC")
    List<Resource> searchByKeyword(String keyword);

    @Select("SELECT * FROM resource_list WHERE is_deleted = 0 AND tab = #{category} ORDER BY create_time DESC")
    List<Resource> searchByCategory(String category);

    @Select("SELECT * FROM resource_list WHERE is_deleted = 0 AND author = #{author} ORDER BY create_time DESC")
    List<Resource> searchByAuthor(String author);

    @Select("<script>" +
            "SELECT * FROM resource_list WHERE is_deleted = 0" +
            "<if test='startTime != null and startTime != \"\"'>" +
            " AND create_time &gt;= #{startTime}" +
            "</if>" +
            "<if test='endTime != null and endTime != \"\"'>" +
            " AND create_time &lt;= #{endTime}" +
            "</if>" +
            " ORDER BY create_time DESC" +
            "</script>")
    List<Resource> searchByTimeRange(@Param("startTime") String startTime, @Param("endTime") String endTime);
}