package top.yuxs.resourcelibrarysystem.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import top.yuxs.resourcelibrarysystem.mapper.BookMapper;
import top.yuxs.resourcelibrarysystem.pojo.Book;
import top.yuxs.resourcelibrarysystem.pojo.Result;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/resources/public")
public class BookController {
    @Resource
    private BookMapper bookMapper;

    @PostMapping("/book/add")
    public Result<String> add (@RequestBody Book book){
        book.setCreateTime(LocalDateTime.now());
        bookMapper.insert(book);
        return Result.success("添加图书成功");
    }
    @GetMapping("/book/findall")
    public Result<List<Book>> findAll(){
        List<Book> books = bookMapper.selectList();
        return Result.success("所有图书查询成功！",books);
    }
    //分页查询
    @GetMapping("/book/page")
    public  Result<IPage<Book>> bookPage (
            @RequestParam Integer pageNum,
            @RequestParam Integer pageSize
    ){
        IPage<Book> bookPage = bookMapper.selectPage(new Page<>(pageNum,pageSize),null);
        return Result.success("分页查询成功",bookPage);
    }
    @GetMapping("/book/delete/{id}")
    public Result<String> deleteById(@PathVariable Long id){
        bookMapper.deleteById(id);

        return Result.success("删除成功！");
    }
}
