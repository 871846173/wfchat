package cn.wildfirechat.app;


import cn.wildfirechat.app.dao.SelectTypeDao;
import cn.wildfirechat.app.dao.UserDao;
import cn.wildfirechat.app.pojo.*;
import cn.wildfirechat.app.service.SelectTypeService;
import cn.wildfirechat.common.ErrorCode;
import cn.wildfirechat.pojos.*;
import cn.wildfirechat.proto.ProtoConstants;
import cn.wildfirechat.sdk.ChatConfig;
import cn.wildfirechat.sdk.MessageAdmin;
import cn.wildfirechat.sdk.UserAdmin;
import cn.wildfirechat.sdk.model.IMResult;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import sun.misc.BASE64Encoder;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static cn.wildfirechat.app.RestResult.RestCode.ERROR_SESSION_NOT_SCANED;
import static cn.wildfirechat.app.RestResult.RestCode.ERROR_SESSION_NOT_VERIFIED;

@org.springframework.stereotype.Service
public class ServiceImpl implements Service {
    static class Count {
        long count;
        long startTime;

        void reset() {
            count = 1;
            startTime = System.currentTimeMillis();
        }

        boolean increaseAndCheck() {
            long now = System.currentTimeMillis();
            if (now - startTime > 86400000) {
                reset();
                return true;
            }
            count++;
            if (count > 10) {
                return false;
            }
            return true;
        }
    }

    private static final Logger LOG = LoggerFactory.getLogger(ServiceImpl.class);
    private static ConcurrentHashMap<String, Record> mRecords = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<String, PCSession> mPCSession = new ConcurrentHashMap<>();

    private static ConcurrentHashMap<String, Count> mCounts = new ConcurrentHashMap<>();

    @Autowired
    private SmsService smsService;

    @Autowired
    private IMConfig mIMConfig;

    @Autowired
    private UserDao userDao;
    @Autowired
    private SelectTypeService selectTypeService;
    @Autowired
    private SelectTypeDao selectTypeDao;

    @Value("${sms.super_code}")
    private String superCode;

    @PostConstruct
    private void init() {
        ChatConfig.initAdmin(mIMConfig.admin_url, mIMConfig.admin_secret);
    }

    @Override
    public RestResult sendCode(String mobile) {
        try {
            if (!Utils.isMobile(mobile)) {
                LOG.error("Not valid mobile {}", mobile);
                return RestResult.error(RestResult.RestCode.ERROR_INVALID_MOBILE);
            }

            Record record = mRecords.get(mobile);
            if (record != null && System.currentTimeMillis() - record.getTimestamp() < 60 * 1000) {
                LOG.error("Send code over frequency. timestamp {}, now {}", record.getTimestamp(), System.currentTimeMillis());
                return RestResult.error(RestResult.RestCode.ERROR_SEND_SMS_OVER_FREQUENCY);
            }
            Count count = mCounts.get(mobile);
            if (count == null) {
                count = new Count();
                mCounts.put(mobile, count);
            }

            if (!count.increaseAndCheck()) {
                LOG.error("Count check failure, already send {} messages today", count.count);
                return RestResult.error(RestResult.RestCode.ERROR_SEND_SMS_OVER_FREQUENCY);
            }

            String code = Utils.getRandomCode(4);

            RestResult.RestCode restCode = smsService.sendCode(mobile, code);
            if (restCode == RestResult.RestCode.SUCCESS) {
                mRecords.put(mobile, new Record(code, mobile));
                return RestResult.ok(restCode);
            } else {
                return RestResult.error(restCode);
            }
        } catch (JSONException e) {
            // json解析错误
            e.printStackTrace();
        }
        return RestResult.error(RestResult.RestCode.ERROR_SERVER_ERROR);
    }

    @Override
    public RestResult login(String mobile, String code, String clientId) {
        boolean allowCode = true;
        String userId = userDao.selectUserId(mobile);
//        session1.setAttribute("selfId",userId);
        SelectType selectType = selectTypeDao.selectType(userId);

        if (selectType != null && selectType.getcCode().equals("1")) {
            allowCode = false;
            LoginResponse response = new LoginResponse();
            response.setAllow(allowCode);
            return RestResult.ok(response);
        }

        if (("13900000000".equals(mobile) || "13900000001".equals(mobile)) && code.equals("556677")) {
            LOG.info("is test account");
        } else if (StringUtils.isEmpty(superCode) || !code.equals(superCode)) {
            Record record = mRecords.get(mobile);
            if (record == null || !record.getCode().equals(code)) {
                LOG.error("not empty or not correct");
                return RestResult.error(RestResult.RestCode.ERROR_CODE_INCORRECT);
            }
            if (System.currentTimeMillis() - record.getTimestamp() > 5 * 60 * 1000) {
                LOG.error("Code expired. timestamp {}, now {}", record.getTimestamp(), System.currentTimeMillis());
                return RestResult.error(RestResult.RestCode.ERROR_CODE_EXPIRED);
            }
        }

        try {
            //使用电话号码查询用户信息。
            IMResult<InputOutputUserInfo> userResult = UserAdmin.getUserByName(mobile);

            //如果用户信息不存在，创建用户
            InputOutputUserInfo user;
            boolean isNewUser = false;
            if (userResult.getErrorCode() == ErrorCode.ERROR_CODE_NOT_EXIST) {
                LOG.info("User not exist, try to create");
                user = new InputOutputUserInfo();
                user.setName(mobile);
                user.setDisplayName(mobile);
                user.setMobile(mobile);
                IMResult<OutputCreateUser> userIdResult = UserAdmin.createUser(user);

                //创建用户允许添加方式
                selectTypeService.createSelectType(userIdResult.getResult().getUserId());
                if (userIdResult.getErrorCode() == ErrorCode.ERROR_CODE_SUCCESS) {
                    user.setUserId(userIdResult.getResult().getUserId());
                    isNewUser = true;
                } else {
                    LOG.info("Create user failure {}", userIdResult.code);
                    return RestResult.error(RestResult.RestCode.ERROR_SERVER_ERROR);
                }
            } else if (userResult.getCode() != 0) {
                LOG.error("Get user failure {}", userResult.code);
                return RestResult.error(RestResult.RestCode.ERROR_SERVER_ERROR);
            } else {
                user = userResult.getResult();
            }

            //使用用户id获取token
            IMResult<OutputGetIMTokenData> tokenResult = UserAdmin.getUserToken(user.getUserId(), clientId);
            if (tokenResult.getErrorCode() != ErrorCode.ERROR_CODE_SUCCESS) {
                LOG.error("Get user failure {}", tokenResult.code);
                return RestResult.error(RestResult.RestCode.ERROR_SERVER_ERROR);
            }

            //返回用户id，token和是否新建
            LoginResponse response = new LoginResponse();
            response.setUserId(user.getUserId());
            response.setToken(tokenResult.getResult().getToken());
            response.setRegister(isNewUser);
            response.setAllow(allowCode);
            return RestResult.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("Exception happens {}", e);
            return RestResult.error(RestResult.RestCode.ERROR_SERVER_ERROR);
        }
    }

    private void sendTextMessage(String toUser, String text) {
        Conversation conversation = new Conversation();
        conversation.setTarget(toUser);
        conversation.setType(ProtoConstants.ConversationType.ConversationType_Private);
        MessagePayload payload = new MessagePayload();
        payload.setType(1);
        payload.setSearchableContent(text);


        try {
            IMResult<SendMessageResult> resultSendMessage = MessageAdmin.sendMessage("admin", conversation, payload);
            if (resultSendMessage != null && resultSendMessage.getErrorCode() == ErrorCode.ERROR_CODE_SUCCESS) {
                LOG.info("send message success");
            } else {
                LOG.error("send message error {}", resultSendMessage != null ? resultSendMessage.getErrorCode().code : "unknown");
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("send message error {}", e.getLocalizedMessage());
        }

    }


    @Override
    public RestResult createPcSession(CreateSessionRequest request) {
        PCSession session = new PCSession();
        session.setClientId(request.getClientId());
        session.setCreateDt(System.currentTimeMillis());
        session.setDuration(300 * 1000); //300 seconds

        if (StringUtils.isEmpty(request.getToken())) {
            request.setToken(UUID.randomUUID().toString());
        }

        session.setToken(request.getToken());
        mPCSession.put(request.getToken(), session);

        SessionOutput output = session.toOutput();

        return RestResult.ok(output);
    }

    @Override
    public RestResult loginWithSession(String token) {
        PCSession session = mPCSession.get(token);
        if (session != null) {
            if (session.getStatus() == 2) {
                //使用用户id获取token
                try {
                    IMResult<OutputGetIMTokenData> tokenResult = UserAdmin.getUserToken(session.getConfirmedUserId(), session.getClientId());
                    if (tokenResult.getCode() != 0) {
                        LOG.error("Get user failure {}", tokenResult.code);
                        return RestResult.error(RestResult.RestCode.ERROR_SERVER_ERROR);
                    }

                    //返回用户id，token和是否新建
                    LoginResponse response = new LoginResponse();
                    response.setUserId(session.getConfirmedUserId());
                    response.setToken(tokenResult.getResult().getToken());
                    return RestResult.ok(response);
                } catch (Exception e) {
                    e.printStackTrace();
                    return RestResult.error(RestResult.RestCode.ERROR_SERVER_ERROR);
                }
            } else {
                if (session.getStatus() == 0)
                    return RestResult.error(ERROR_SESSION_NOT_SCANED);
                else {
                    return RestResult.error(ERROR_SESSION_NOT_VERIFIED);
                }
            }
        } else {
            return RestResult.error(RestResult.RestCode.ERROR_SESSION_EXPIRED);
        }
    }

    @Override
    public RestResult scanPc(String token) {
        PCSession session = mPCSession.get(token);
        if (session != null) {
            SessionOutput output = session.toOutput();
            if (output.getExpired() > 0) {
                session.setStatus(1);
                output.setStatus(1);
                return RestResult.ok(output);
            } else {
                return RestResult.error(RestResult.RestCode.ERROR_SESSION_EXPIRED);
            }
        } else {
            return RestResult.error(RestResult.RestCode.ERROR_SESSION_EXPIRED);
        }
    }

    @Override
    public RestResult confirmPc(ConfirmSessionRequest request) {
        PCSession session = mPCSession.get(request.getToken());
        if (session != null) {
            SessionOutput output = session.toOutput();
            if (output.getExpired() > 0) {
                //todo 检查IMtoken，确认用户id不是冒充的
                session.setStatus(2);
                output.setStatus(2);
                session.setConfirmedUserId(request.getUser_id());
                return RestResult.ok(output);
            } else {
                return RestResult.error(RestResult.RestCode.ERROR_SESSION_EXPIRED);
            }
        } else {
            return RestResult.error(RestResult.RestCode.ERROR_SESSION_EXPIRED);
        }
    }

    @Override
    public RestResult loginByPassword(String mobile, String password, String clientId) {
        if (("13900000000".equals(mobile) || "13900000001".equals(mobile))) {
            LOG.info("is test account");
        } else if (StringUtils.isEmpty(superCode)) {
            Record record = mRecords.get(mobile);
            if (record == null) {
                LOG.error("not empty or not correct");
                return RestResult.error(RestResult.RestCode.ERROR_CODE_INCORRECT);
            }
            if (System.currentTimeMillis() - record.getTimestamp() > 5 * 60 * 1000) {
                LOG.error("Code expired. timestamp {}, now {}", record.getTimestamp(), System.currentTimeMillis());
                return RestResult.error(RestResult.RestCode.ERROR_CODE_EXPIRED);
            }
        }

        try {
            //使用电话号码和密码查询用户信息。
            if (password == null || password.equals(" ")) {
                return RestResult.error(RestResult.RestCode.ERROR_CODE_PASSWORD);
            }

            String passwdMd5 = this.getMD5(password);
            User userResult = userDao.getUser(mobile, passwdMd5);
            //用户不存在
            if (userResult == null || userResult.equals(" ")) {
                return RestResult.error(RestResult.RestCode.ERROR_CODE_PASSWORD);
            }

            InputOutputUserInfo user;
            boolean isNewUser = false;

            //使用用户id获取token
            IMResult<OutputGetIMTokenData> tokenResult = UserAdmin.getUserToken(userResult.getUserId(), clientId);
            if (tokenResult.getErrorCode() != ErrorCode.ERROR_CODE_SUCCESS) {
                LOG.error("Get user failure {}", tokenResult.code);
                return RestResult.error(RestResult.RestCode.ERROR_SERVER_ERROR);
            }

            //返回用户id，token和是否新建
            LoginResponse response = new LoginResponse();
            response.setUserId(userResult.getUserId());
            response.setToken(tokenResult.getResult().getToken());
            response.setRegister(isNewUser);
            return RestResult.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("Exception happens {}", e);
            return RestResult.error(RestResult.RestCode.ERROR_SERVER_ERROR);
        }
    }

    @Override
    public User getUser(String mobile, String password) {
        String passwdMd5 = this.getMD5(password);
        System.out.println(passwdMd5);
        return userDao.getUser(mobile, passwdMd5);
    }

    //修改密码
    @Override
    public RestResult updatePassword(String userId, String oldPassword, String newPassword) {
        if (StringUtils.isEmpty(userId)) {
            return RestResult.error(RestResult.RestCode.ERROR_INVALID_USER);
        }
        if (!StringUtils.isEmpty(oldPassword)) {
            String password = this.getMD5(oldPassword);
            User user = userDao.verifyPassword(userId, password);
            if (StringUtils.isEmpty(user)) {
                return RestResult.error(RestResult.RestCode.ERROR_CODE_PASSWORD);
            }
        }
        String password = this.getMD5(newPassword);
        int status = userDao.updatePassword(userId, password);
        if (status == 0) {
            return RestResult.error(RestResult.RestCode.ERROR_CODE_PASSWORD);
        }
        if (status == 1) {
            return RestResult.ok(RestResult.RestCode.SUCCESS);
        }
        return RestResult.error(RestResult.RestCode.ERROR_CODE_PASSWORD);
    }

    @Override
    public RestResult forgetPassword(String mobile, String code, String password) {

        Record record = mRecords.get(mobile);
        if (record == null || !record.getCode().equals(code)) {
            LOG.error("not empty or not correct");
            return RestResult.error(RestResult.RestCode.ERROR_CODE_INCORRECT);
        }
        if (System.currentTimeMillis() - record.getTimestamp() > 5 * 60 * 1000) {
            LOG.error("Code expired. timestamp {}, now {}", record.getTimestamp(), System.currentTimeMillis());
            return RestResult.error(RestResult.RestCode.ERROR_CODE_EXPIRED);
        }

        int status = userDao.updatePasswordByMobile(mobile, this.getMD5(password));
        if (status == 0) {
            return RestResult.error(RestResult.RestCode.ERROR_INVALID_USER);
        }
        return RestResult.ok(RestResult.RestCode.SUCCESS);
    }

    public String getMD5(String password) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        BASE64Encoder base64en = new BASE64Encoder();
        String passwdMd5 = null;
        try {
            passwdMd5 = base64en.encode(md5.digest(password.getBytes("utf-8")));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return passwdMd5;
    }

    @Override
    public RestResult checkUserOnline(String userId,String selfId) {

        User user = userDao.checkUserOnline(userId);
        User userSelf=userDao.checkUserOnline(selfId);
        int selfOnlineStatus=userSelf.getOnline();

        if (StringUtils.isEmpty(user)) {
            return RestResult.error(RestResult.RestCode.ERROR_INVALID_USER);
        }
        String time = user.getUpdateTime();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        user.setTimestamp(date.getTime());
        if(selfOnlineStatus==2){
            user.setOnline(2);
        }

        return RestResult.ok(user);
    }

    @Override
    public RestResult updateUserOnline(String userId, Integer online) {
        User user = userDao.checkUserOnline(userId);

        if(user.getOnline()==2){
            return  RestResult.ok("隐身不能设置该状态");
        }
        int a = userDao.updateUserOnline(userId, online);
        if (a >= 1) {
            return RestResult.ok("修改用户状态成功");
        }
        return RestResult.error(RestResult.RestCode.ERROR_SERVER_NOT_IMPLEMENT);
    }

    @Override
    public RestResult updateUserStealth(String userId) {

        User user = userDao.checkUserOnline(userId);
        if (StringUtils.isEmpty(user)) {
            return RestResult.error(RestResult.RestCode.ERROR_INVALID_USER);
        }
        if (user.getOnline() == 0) {
            int a = userDao.updateUserOnline(userId, 2);
            if (a >= 1) {
                return RestResult.ok("用户隐身成功");
            }
        }
        if (user.getOnline() == 2) {
            int a = userDao.updateUserOnline(userId, 0);
            if (a >= 1) {
                return RestResult.ok("用户取消隐身");
            }
        }
        return RestResult.error(RestResult.RestCode.ERROR_SERVER_NOT_IMPLEMENT);
    }
}
