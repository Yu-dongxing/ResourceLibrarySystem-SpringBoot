package top.yuxs.resourcelibrarysystem.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.yuxs.resourcelibrarysystem.mapper.UserMapper;
import top.yuxs.resourcelibrarysystem.pojo.Users;
import top.yuxs.resourcelibrarysystem.service.UserService;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public void add(Users users) {
        users.setUpdateTime(LocalDateTime.now());
        users.setCreateTime(LocalDateTime.now());
        userMapper.insert(users);
    }

    @Override
    public Users findPhoneNumber(String phoneNumber) {
        return userMapper.selectByPhoneNumber(phoneNumber);
    }
}
