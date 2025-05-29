package top.yuxs.resourcelibrarysystem.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("sys_config")
public class SysConfig {
    /**
     * 配置创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime configCreateTime;
    /**
     * 配置id
     */
    @TableId(type = IdType.AUTO)
    private long configId;
    /**
     * 配置字段
     */
    private String configP;
    /**
     * 配置名称
     */
    private String configTitle;
    /**
     * 配置更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime configUpdateTime;
    /**
     * 配置值
     */
    private String configView;
}
