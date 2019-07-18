//package cn.wildfirechat.app.controller;
//
//import cn.wildfirechat.app.Service;
//import cn.wildfirechat.app.pojo.LoginRequest;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//public class userController {
//    @Autowired
//    private Service mService;
//
//    @PostMapping(value = "/loginByPassword", produces = "application/json;charset=UTF-8")
//    public Object loginByPassword(@RequestBody LoginRequest request) {
//        return mService.loginByPassword(request.getMobile(), request.getPassword(), request.getClientId());
//    }
//
//    @PostMapping(value = "/updatePassword", produces = "application/json;charset=UTF-8")
//    public Object updatePassword(@RequestBody LoginRequest request) {
//        return mService.updatePassword(request.getUserId(), request.getPassword());
//    }
//
//}
