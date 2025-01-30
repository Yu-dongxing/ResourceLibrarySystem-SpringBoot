package top.yuxs.resourcelibrarysystem.service;

import top.yuxs.resourcelibrarysystem.pojo.Users;

public interface UserService {
    void add(Users users);

    Users findPhoneNumber(String phoneNumber);

    Users selectById(long id);
}
