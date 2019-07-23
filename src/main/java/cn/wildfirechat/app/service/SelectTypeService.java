package cn.wildfirechat.app.service;

import cn.wildfirechat.app.RestResult;

public interface SelectTypeService {

    RestResult updateSelectType(String userId, String cMobile, String cGroup, String cQrcode, String cCard);

    RestResult createSelectType(String userId);

    RestResult selectType(String mobile);
}
