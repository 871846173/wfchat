package cn.wildfirechat.app.dao;

import cn.wildfirechat.app.RestResult;
import cn.wildfirechat.app.pojo.Message;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MessageDao {
    //    List<Message> selectMessageRead(@Param(value = "from")String from ,@Param(value = "target") String target);//查询是否是已读消息
//
    Message selectMessageRead(String mid);//查询是否是已读消息

    int updateMessageRead(@Param(value = "selfId")String selfId,@Param(value = "userId") String userId);//将消息设置为已读

    int findNoReadCount(@Param(value = "selfId")String selfId,@Param(value = "userId") String userId);

   // int deleteMessage(@Param(value = "mId")String mId,@Param(value = "time")int time,int read);
    int deleteMessage(@Param(value = "mId")String mId);

    List<Message>selectNoReadMessage(@Param(value = "selfId")String selfId,@Param(value = "userId") String userId);

    int onReadDestroy(@Param(value = "selfId")String selfId,@Param(value = "userId") String userId,String time);

    int setRead(String mid);

}
