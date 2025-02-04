package top.yuxs.resourcelibrarysystem.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class UserDTO {
//    用户信息
    private Long id;
    private String username;
    private String email;
    private String phoneNumber;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
//    角色权限信息
    private String roleName;
    private int roleId;
    private List<String> permissions;
}
