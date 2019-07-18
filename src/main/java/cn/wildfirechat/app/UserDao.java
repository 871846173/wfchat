package cn.wildfirechat.app;

import cn.wildfirechat.app.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserDao {
    User getUser(@Param(value = "mobile") String mobile, @Param(value = "password") String password);

    int updatePassword(@Param(value = "userId") String userId, @Param(value = "password") String password);

    String selectPassword(@Param(value = "userId") String userId);
}
