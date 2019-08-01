package cn.wildfirechat.app;

import cn.wildfirechat.app.dao.MessageDao;
import cn.wildfirechat.app.pojo.Message;
import cn.wildfirechat.app.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.List;

@org.springframework.stereotype.Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageDao messageDao;

    @Override
    public RestResult selectMessageRead(String mid) {
        Message message = messageDao.selectMessageRead(mid);
        if (message == null) {
            return RestResult.error(RestResult.RestCode.ERROR_SERVER_NOT_IMPLEMENT);
        }
        return RestResult.ok(message.getRead());
    }

    @Override
    public RestResult updateMessageRead(String selfId, String userId) {
       int success=0;

//        //对方打开消息的时候调用，不知道什么时候是对方打开消息框
//
//             success = messageDao.updateMessageRead(userId, selfId);
//
//        if (success != 0) {
//            return RestResult.ok("已读");
//        } else {
//            return RestResult.error(RestResult.RestCode.ERROR_SERVER_NOT_IMPLEMENT);
//        }
       List<Message> list= messageDao.selectNoReadMessage(userId, selfId);
       for(int i=0;i<list.size();i++){
           if(!list.get(i).getFrom().equals(selfId)){//
               messageDao.setRead(list.get(i).getMid());//将我发送的消息设置为已读
           }
           success++;
       }

        if (success != 0) {
            return RestResult.ok("已读");
        } else {
           return RestResult.ok("暂无未读消息");
        }



//return  null;
    }

    @Override
    public RestResult findNoReadCount(String selfId, String userId) {

        return RestResult.ok(messageDao.findNoReadCount(selfId, userId));
    }

    @Override
    public RestResult deleteMessage(String mId, int time) {



        countDown(time);
        int isSuccess = messageDao.deleteMessage(mId);


        if (isSuccess != 0) {
            return RestResult.ok("阅后即焚删除消息成功");
        } else {
            return RestResult.error(RestResult.RestCode.ERROR_SERVER_NOT_IMPLEMENT);
        }

        //是否阅后即焚，if(阅后即焚)，list.get(i).getreadtime-now>=time 删除
    }

//倒计时
    public synchronized  void countDown(int time) {
        for (int i = time; i >= 1; i--) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }
}
