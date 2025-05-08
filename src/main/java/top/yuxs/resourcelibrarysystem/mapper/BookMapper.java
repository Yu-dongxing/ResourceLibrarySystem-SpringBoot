package top.yuxs.resourcelibrarysystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import top.yuxs.resourcelibrarysystem.pojo.Book;

import java.util.List;

@Mapper
public interface BookMapper  extends BaseMapper<Book> {
    @Select("SELECT *  FROM book")
    List<Book> selectList();
}
