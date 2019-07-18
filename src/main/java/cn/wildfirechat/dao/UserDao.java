package cn.wildfirechat.dao;

import cn.wildfirechat.app.pojo.LoginResponse;
import cn.wildfirechat.app.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserDao {
    User getUser(@Param(value = "mobile") String mobile, @Param(value = "password") String password);
    int updatePassword(@Param(value = "password") String password,@Param(value = "id") String id);
}
