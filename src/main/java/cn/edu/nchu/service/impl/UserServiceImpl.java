package cn.edu.nchu.service.impl;

import cn.edu.nchu.entity.UserEntity;
import cn.edu.nchu.mappers.UserMapper;
import cn.edu.nchu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by liuwentao on 2020-03-14 11:34
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public UserEntity findUserByUserNamePassWord(String username, String password) {
        return userMapper.findUserByUserNamePassWord(username, password);
    }

    @Override
    public UserEntity findUserByUserID(int userID) {
        return userMapper.findUserByUserID(userID);
    }

}
