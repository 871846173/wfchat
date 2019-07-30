package cn.wildfirechat.app.service;

import cn.wildfirechat.app.RestResult;
import org.springframework.beans.factory.annotation.Autowired;

public interface MessageService {

    RestResult selectMessageRead(String mid);//查询是否是已读消息

    RestResult updateMessageRead(String selfId,String userId);//将消息设置为已读
}
