package top.yuxs.resourcelibrarysystem.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import java.util.List;

@Data
public class RoleCreateDTO {
    @NotBlank(message = "角色名称不能为空")
    private String name;
    
    private String description;
    
    @NotEmpty(message = "权限列表不能为空")
    private List<Integer> permissionIds;
} 