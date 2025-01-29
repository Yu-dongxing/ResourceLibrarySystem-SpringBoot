package top.yuxs.resourcelibrarysystem.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

//用户信息和用户角色和用户权限类
@Data
public class User {
    private Long id;
    private String username;
    private String email;
    private String phoneNumber;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
    private String roleName;
    private int roleId;
    private List<String> permissions;
}