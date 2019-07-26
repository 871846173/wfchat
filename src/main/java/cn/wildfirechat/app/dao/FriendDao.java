package cn.wildfirechat.app.dao;

import cn.wildfirechat.app.pojo.Friend;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface FriendDao {

    int deleteFriend(Friend friend);

}
