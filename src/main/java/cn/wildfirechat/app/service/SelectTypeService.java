package cn.wildfirechat.app.service;

import cn.wildfirechat.app.RestResult;

public interface SelectTypeService {

    RestResult updateSelectType(String userId, int cMobile, int cGroup, int cQrcode, int cCard);

    RestResult insertSelectType(String userId, int cMobile, int cGroup, int cQrcode, int cCard);
}
