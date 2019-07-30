package cn.wildfirechat.app;

import cn.wildfirechat.app.service.MessageService;

@org.springframework.stereotype.Service
public class MessageServiceImpl implements MessageService {

    @Override
    public RestResult selectMessageRead(Integer mid) {
        return null;
    }

    @Override
    public RestResult updateMessageRead(Integer mid, Integer read) {
        return null;
    }
}
