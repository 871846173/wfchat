package cn.wildfirechat.app.service;

import cn.wildfirechat.app.RestResult;

public interface MessageService {

    RestResult selectMessageRead(Integer mid);

    RestResult updateMessageRead(Integer mid, Integer read);
}
