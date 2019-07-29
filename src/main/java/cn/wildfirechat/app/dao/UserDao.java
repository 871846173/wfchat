package cn.wildfirechat.app.dao;

import cn.wildfirechat.app.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserDao {
    User getUser(@Param(value = "mobile") String mobile, @Param(value = "password") String password);

    User verifyPassword(@Param(value = "userId") String userId, @Param(value = "password") String password);

    int updatePassword(@Param(value = "userId") String userId, @Param(value = "password") String password);

    int updatePasswordByMobile(@Param(value = "mobile") String mobile, @Param(value = "password") String password);

    String selectUserId(@Param(value = "mobile") String mobile);

    User checkUserOnline(@Param(value = "userId") String userId);

    int updateUserOnline(@Param(value = "userId") String userId, @Param(value = "online") Integer online);
}
