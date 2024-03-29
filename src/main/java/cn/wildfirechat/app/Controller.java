package cn.wildfirechat.app;

import cn.wildfirechat.app.pojo.*;
import cn.wildfirechat.app.service.CollectionService;
import cn.wildfirechat.app.service.FriendService;
import cn.wildfirechat.app.service.MessageService;
import cn.wildfirechat.app.service.SelectTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class Controller {
    @Autowired
    private Service mService;
    @Autowired
    private CollectionService collectionService;
    @Autowired
    private SelectTypeService selectTypeService;
    @Autowired
    private FriendService friendService;
    @Autowired
    private MessageService messageService;

    @PostMapping(value = "/send_code", produces = "application/json;charset=UTF-8")
    public Object sendCode(@RequestBody SendCodeRequest request) {
        return mService.sendCode(request.getMobile());
    }

    @PostMapping(value = "/login", produces = "application/json;charset=UTF-8")
    public Object login(@RequestBody LoginRequest request) {
        return mService.login(request.getMobile(), request.getCode(), request.getClientId());
    }


    /* PC扫码操作
    1, PC -> App     创建会话
    2, PC -> App     轮询调用session_login进行登陆，如果已经扫码确认返回token，否则反正错误码9（已经扫码还没确认)或10(还没有被扫码)。
     */
    @PostMapping(value = "/pc_session", produces = "application/json;charset=UTF-8")
    public Object createPcSession(@RequestBody CreateSessionRequest request) {
        return mService.createPcSession(request);
    }

    @PostMapping(value = "/session_login/{token}", produces = "application/json;charset=UTF-8")
    public Object loginWithSession(@PathVariable("token") String token) {
        return mService.loginWithSession(token);
    }

    /* 手机扫码操作
    1，扫码，调用/scan_pc接口。
    2，调用/confirm_pc 接口进行确认
     */
    @PostMapping(value = "/scan_pc/{token}", produces = "application/json;charset=UTF-8")
    public Object scanPc(@PathVariable("token") String token) {
        return mService.scanPc(token);
    }

    @PostMapping(value = "/confirm_pc", produces = "application/json;charset=UTF-8")
    public Object confirmPc(@RequestBody ConfirmSessionRequest request) {
        return mService.confirmPc(request);
    }

    @PostMapping(value = "/loginByPassword", produces = "application/json;charset=UTF-8")
    public Object loginByPassword(@RequestBody LoginRequest request) {
        return mService.loginByPassword(request.getMobile(), request.getPassword(), request.getClientId());
    }

    @PostMapping(value = "/updatePassword", produces = "application/json;charset=UTF-8")
    public Object updatePassword(@RequestBody LoginRequest request) {
        return mService.updatePassword(request.getUserId(), request.getOldPassword(), request.getNewPassword());
    }

    //忘记登录密码
    @PostMapping(value = "/forgetPassword", produces = "application/json;charset=UTF-8")
    public Object forgetPassword(@RequestBody LoginRequest request) {
        return mService.forgetPassword(request.getMobile(), request.getCode(), request.getPassword());
    }

    @PostMapping(value = "/addCollection", produces = "application/json;charset=UTF-8")
    public Object addCollection(@RequestBody Collection collection) {
        return collectionService.addCollection(collection.getUid(), collection.getMid());
    }

    @PostMapping(value = "/deleteCollection", produces = "application/json;charset=UTF-8")
    public Object deleteCollection(@RequestBody Collection collection) {
        return collectionService.deleteCollection(collection.getUid(), collection.getMid());
    }

    @PostMapping(value = "/getCollectionListWithUid", produces = "application/json;charset=UTF-8")
    public RestResult getCollectionListWithUid(@RequestBody Collection collection) {
        return collectionService.getCollectionListWithUid(collection.getUid(), collection.getPage(), collection.getSize());
    }

    //修改用户添加方式（0允许1不允许）
    @PostMapping(value = "/updateSelectType", produces = "application/json;charset=UTF-8")
    public Object updateSelectType(@RequestBody SelectType selectType) {
        return selectTypeService.updateSelectType(selectType.getUserId(), selectType.getcMobile(), selectType.getcGroup(),
                selectType.getcQrcode(), selectType.getcCard());
    }

    //查询用户添加方式
    @PostMapping(value = "/selectType", produces = "application/json;charset=UTF-8")
    public Object selectType(@RequestBody SelectType selectType) {
        return selectTypeService.selectType(selectType.getMobile());
    }

    //是否根据验证码登录
    @PostMapping(value = "/updateCCode", produces = "application/json;charset=UTF-8")
    public Object updateCCode(@RequestBody SelectType selectType) {
        return selectTypeService.updateCCode(selectType.getUserId(), selectType.getcCode());
    }

    @PostMapping(value = "/selectCCode", produces = "application/json;charset=UTF-8")
    public Object selectCCode(@RequestBody SelectType selectType) {
        return selectTypeService.selectCCode(selectType.getUserId());
    }

    //删除好友
    @PostMapping(value = "/deleteFriend", produces = "application/json;charset=UTF-8")
    public Object deleteFriend(@RequestBody Friend friend) {
        return friendService.deleteFriend(friend.getUserId(), friend.getFriendUid(), friend.getState());
    }

    //查询用户在线状态
    @PostMapping(value = "/checkUserOnline", produces = "application/json;charset=UTF-8")
    public Object checkUserOnline(@RequestBody User user) {
        return mService.checkUserOnline(user.getUserId(),user.getSelfId());
    }

    //设置用户在线状态
    @PostMapping(value = "/updateUserOnline", produces = "application/json;charset=UTF-8")
    public Object updateUserOnline(@RequestBody User user) {
        return mService.updateUserOnline(user.getUserId(), user.getOnline());
    }

    //设置用户隐身模式
    @PostMapping(value = "/updateUserStealth", produces = "application/json;charset=UTF-8")
    public Object updateUserStealth(@RequestBody User user) {
        return mService.updateUserStealth(user.getUserId());
    }

    //查询消息已读、未读状态
    @PostMapping(value = "/selectMessageRead", produces = "application/json;charset=UTF-8")
    public Object selectMessageRead(@RequestBody Message message) {
        return messageService.selectMessageRead(message.getMid());
    }

    //设置消息已读、未读
    @PostMapping(value = "/updateMessageRead", produces = "application/json;charset=UTF-8")
    public Object updateMessageRead(@RequestBody Message message) {
        return messageService.updateMessageRead(message.getSelfId(),message.getUserId());
    }

    @PostMapping(value = "/findNoReadCount", produces = "application/json;charset=UTF-8")
    public Object findNoReadCount(@RequestBody Message message) {
        return messageService.findNoReadCount(message.getSelfId(),message.getUserId());
    }

    @PostMapping(value = "/deleteMessage", produces = "application/json;charset=UTF-8")
    public Object deleteMessage(@RequestBody Message message) {
        return messageService.deleteMessage(message.getMid(),message.getTime());
    }
}
