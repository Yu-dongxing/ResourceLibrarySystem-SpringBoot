package top.yuxs.resourcelibrarysystem.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import top.yuxs.resourcelibrarysystem.pojo.FileData;

import java.util.List;

@Mapper
public interface FileDataMapper {
    // 查询所有文件信息
    @Select("SELECT * FROM file_date WHERE is_deleted = 0")
    List<FileData> findAll();

    // 根据ID查询文件信息
    @Select("SELECT * FROM file_date WHERE id = #{id} AND is_deleted = 0")
    FileData findById(Long id);

    // 插入文件信息
    int insertFileData(FileData fileData);

    // 更新文件信息
    int updateFileData(FileData fileData);

    // 删除文件信息（逻辑删除）
    int deleteFileData(Long id);
}
