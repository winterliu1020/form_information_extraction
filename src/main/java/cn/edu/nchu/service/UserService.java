package cn.edu.nchu.service;

import cn.edu.nchu.entity.UserEntity;

/**
 * Created by liuwentao on 2020-03-14 11:33
 */
public interface UserService {
    UserEntity findUserByUserNamePassWord(String username, String password);

    UserEntity findUserByUserID(int userID);
}
