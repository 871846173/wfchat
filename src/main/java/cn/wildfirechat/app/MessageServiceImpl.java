package cn.wildfirechat.app;

import cn.wildfirechat.app.dao.MessageDao;
import cn.wildfirechat.app.pojo.Message;
import cn.wildfirechat.app.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageDao messageDao;
    @Override
    public RestResult selectMessageRead(String mid) {
        Message message=messageDao.selectMessageRead(mid);
        if(message==null){
            return RestResult.error(RestResult.RestCode.ERROR_SERVER_NOT_IMPLEMENT);
        }
        return RestResult.ok(message.getRead());
    }

    @Override
    public RestResult updateMessageRead(String selfId,String userId) {
     int success=messageDao.updateMessageRead(userId,selfId);
     if(success!=0){
         return RestResult.ok("已读");
     }else{
         return RestResult.error(RestResult.RestCode.ERROR_SERVER_NOT_IMPLEMENT);
     }

    }
}
