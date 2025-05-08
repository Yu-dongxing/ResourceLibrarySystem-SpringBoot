package top.yuxs.resourcelibrarysystem.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@TableName("book")
@Data
public class Book {
    @TableId(type = IdType.AUTO)
    /*
      id
     */
    private Long id;

    /**
     * 图书编号
     */
    private String isbn;

    /**
     * 名称
     */
    private String name;

    /**
     * 价格
     */
    private Double price;

    /**
     * 作者
     */
    private String author;

    /**
     * 出版社
     */
    private String publisher;

    /**
     * 出版时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 0：未归还 1：已归还
     */
    private String status;

    /**
     * 此书被借阅次数
     */
    private Integer borrownum;
}
