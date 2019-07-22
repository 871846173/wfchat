package cn.wildfirechat.app;

import cn.wildfirechat.app.dao.SelectTypeDao;
import cn.wildfirechat.app.pojo.SelectType;
import cn.wildfirechat.app.service.SelectTypeService;
import com.cloopen.rest.sdk.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

@org.springframework.stereotype.Service
public class SelectTypeServiceImpl implements SelectTypeService {
    @Autowired
    private SelectTypeDao selectTypeDao;

    @Override
    public RestResult updateSelectType(String userId, int cMobile, int cGroup, int cQrcode, int cCard) {
        if (StringUtils.isEmpty(userId)) {
            return RestResult.error(RestResult.RestCode.ERROR_INVALID_USER);
        }
        SelectType selectType = new SelectType();
        selectType.setUserId(userId);
        if (!StringUtils.isEmpty(cMobile)) {
            selectType.setcMobile(cMobile);
        }
        if (!StringUtils.isEmpty(cGroup)) {
            selectType.setcGroup(cGroup);
        }
        if (!StringUtils.isEmpty(cQrcode)) {
            selectType.setcQrcode(cQrcode);
        }
        if (!StringUtils.isEmpty(cCard)) {
            selectType.setcCard(cCard);
        }

        int status = selectTypeDao.updateSelectType(selectType);
        if (status == 1) {
            return RestResult.ok(RestResult.RestCode.SUCCESS);
        } else {
            return RestResult.error(RestResult.RestCode.ERROR_SERVER_ERROR);
        }

    }

    @Override
    public RestResult insertSelectType(String userId, int cMobile, int cGroup, int cQrcode, int cCard) {
        if (StringUtils.isEmpty(userId)) {
            return RestResult.error(RestResult.RestCode.ERROR_INVALID_USER);
        }
        SelectType selectType = new SelectType();
        selectType.setUserId(userId);
        selectType.setcMobile(0);
        selectType.setcGroup(0);
        selectType.setcQrcode(0);
        selectType.setcCard(0);
//        selectType.setCreateTime(System.currentTimeMillis());

        int status = selectTypeDao.insertSelectType(selectType);
        if (status == 1) {
            return RestResult.ok(RestResult.RestCode.SUCCESS);
        } else {
            return RestResult.error(RestResult.RestCode.ERROR_SERVER_ERROR);
        }

    }
}
