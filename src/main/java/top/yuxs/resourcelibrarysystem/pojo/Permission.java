package top.yuxs.resourcelibrarysystem.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("permissions")
public class Permission {
        @TableId(type = IdType.AUTO)
        private Integer id;
        private String name;
        private String description;
}
