package top.yuxs.resourcelibrarysystem.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.yuxs.resourcelibrarysystem.mapper.UserRolesMapper;
import top.yuxs.resourcelibrarysystem.service.UserRolesService;
@Service
public class UserRolesServiceImpl implements UserRolesService {
    @Autowired
    private UserRolesMapper userRolesMapper;
    public void add(long userID,int roleId){
        userRolesMapper.insert(userID,roleId);
    }
}
