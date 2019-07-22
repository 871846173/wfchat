package cn.wildfirechat.app;


import cn.wildfirechat.app.pojo.ConfirmSessionRequest;
import cn.wildfirechat.app.pojo.CreateSessionRequest;
import cn.wildfirechat.app.pojo.User;

public interface Service {
    RestResult sendCode(String mobile);
    RestResult login(String mobile, String code, String clientId);
    RestResult loginByPassword(String mobile, String code, String clientId);


    RestResult createPcSession(CreateSessionRequest request);
    RestResult loginWithSession(String token);

    RestResult scanPc(String token);
    RestResult confirmPc(ConfirmSessionRequest request);
    User getUser(String mobile, String password);

    RestResult updatePassword(String userId,String oldPassword, String newPassword);
    RestResult forgetPassword(String mobile,String code,String password);
}
