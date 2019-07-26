package cn.wildfirechat.app;

import cn.wildfirechat.app.dao.SelectTypeDao;
import cn.wildfirechat.app.dao.UserDao;
import cn.wildfirechat.app.pojo.SelectType;
import cn.wildfirechat.app.service.SelectTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

@org.springframework.stereotype.Service
public class SelectTypeServiceImpl implements SelectTypeService {
    @Autowired
    private SelectTypeDao selectTypeDao;
    @Autowired
    private UserDao userDao;

    @Override
    public RestResult updateSelectType(String userId, String cMobile, String cGroup, String cQrcode, String cCard) {
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
    public RestResult createSelectType(String userId) {
        if (StringUtils.isEmpty(userId)) {
            return RestResult.error(RestResult.RestCode.ERROR_INVALID_USER);
        }
        SelectType selectType = new SelectType();
        selectType.setUserId(userId);
        selectType.setcMobile("0");
        selectType.setcGroup("0");
        selectType.setcQrcode("0");
        selectType.setcCard("0");
        selectType.setcCode("0");
//        selectType.setCreateTime(System.currentTimeMillis());

        int status = selectTypeDao.insertSelectType(selectType);
        if (status == 1) {
            return RestResult.ok(RestResult.RestCode.SUCCESS);
        } else {
            return RestResult.error(RestResult.RestCode.ERROR_SERVER_ERROR);
        }

    }

    @Override
    public RestResult selectType(String mobile) {

        String userId = userDao.selectUserId(mobile);
        if (StringUtils.isEmpty(userId)) {
            return RestResult.error(RestResult.RestCode.ERROR_INVALID_USER);
        }

        SelectType selectType = selectTypeDao.selectType(userId);
        if (StringUtils.isEmpty(selectType)) {
            return RestResult.error(RestResult.RestCode.ERROR_SERVER_ERROR);
        }
        return RestResult.ok(selectType);
    }

    @Override
    public RestResult updateCCode(String userId, String cCode) {
        if (StringUtils.isEmpty(userId)) {
            return RestResult.error(RestResult.RestCode.ERROR_INVALID_USER);
        }
        SelectType selectType = new SelectType();
        selectType.setUserId(userId);
        if (!StringUtils.isEmpty(cCode)) {
            selectType.setcCode(cCode);
        }

        int status = selectTypeDao.updateSelectType(selectType);
        if (status == 1) {
            return RestResult.ok(RestResult.RestCode.SUCCESS);
        } else {
            return RestResult.error(RestResult.RestCode.ERROR_SERVER_ERROR);
        }
    }

    @Override
    public RestResult selectCCode(String userId) {

        if (StringUtils.isEmpty(userId)) {
            return RestResult.error(RestResult.RestCode.ERROR_INVALID_USER);
        }

        SelectType selectType = selectTypeDao.selectCCode(userId);
        if (StringUtils.isEmpty(selectType)) {
            return RestResult.error(RestResult.RestCode.ERROR_SERVER_ERROR);
        }
        return RestResult.ok(selectType);
    }

}
