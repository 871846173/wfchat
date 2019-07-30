package cn.wildfirechat.app;

import com.cloopen.rest.sdk.CCPRestSDK;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Set;

@Service
public class SmsServiceImpl implements SmsService {
    private static final Logger LOG = LoggerFactory.getLogger(SmsServiceImpl.class);


    @Value("${sms.verdor}")
    private int smsVerdor;

    @Autowired
    private TencentSMSConfig mTencentSMSConfig;

    @Override
    public RestResult.RestCode sendCode(String mobile, String code) {
        if (smsVerdor == 1) {
            return sendTencentCode(mobile, code);
        } else {
            return RestResult.RestCode.ERROR_SERVER_NOT_IMPLEMENT;
        }
    }

    public RestResult.RestCode sendTencentCode(String mobile, String code) {
        String[] params = {code};
        HashMap<String, Object> result = null;
        //初始化sdk
        CCPRestSDK restAPI = new CCPRestSDK();
        // 初始化服务器地址和端口，格式如下，服务器地址不需要写https://
        restAPI.init(mTencentSMSConfig.serverip, mTencentSMSConfig.serverport);
        // 初始化主帐号和主帐号TOKEN
        restAPI.setAccount(mTencentSMSConfig.accountsid, mTencentSMSConfig.accounttoken);
        // 初始化应用ID
        restAPI.setAppId(mTencentSMSConfig.appid);
        //模板Id，不带此参数查询全部可用模板
        result = restAPI.sendTemplateSMS(mobile, mTencentSMSConfig.templateId, params);
        System.out.println("QuerySMSTemplate result=" + result);
        if ("000000".equals(result.get("statusCode"))) {
            //正常返回输出data包体信息（map）
            HashMap<String, Object> data = (HashMap<String, Object>) result.get("data");
            Set<String> keySet = data.keySet();
            for (String key : keySet) {
                Object object = data.get(key);
                System.out.println(key + " = " + object);
            }
            return RestResult.RestCode.SUCCESS;
        } else {
            //异常返回输出错误码和错误信息
            System.out.println("错误码=" + result.get("statusCode") + " 错误信息= " + result.get("statusMsg"));
            LOG.error("Failure to send SMS {}", result);
            return RestResult.RestCode.ERROR_SERVER_ERROR;
        }

    }
}
