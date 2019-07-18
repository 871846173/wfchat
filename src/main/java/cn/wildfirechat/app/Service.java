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

    /**
     * 通过id修改密码
     * @param password
     * @param userId
     * @return
     */
    RestResult updatePassword(String userId,String password);

    RestResult selectPassword(String userId);
    RestResult addCollection(String uid,String mid);
    RestResult deleteCollection(String mid);
}
