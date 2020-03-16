package cn.edu.nchu.mappers;

import cn.edu.nchu.entity.UserEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by liuwentao on 2020-03-14 11:27
 */
@Repository
public interface UserMapper {
    UserEntity findUserByUserNamePassWord(@Param("username") String username,@Param("password") String password);

    UserEntity findUserByUserID(Integer userID);
}
