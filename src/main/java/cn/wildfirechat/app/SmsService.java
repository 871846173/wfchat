package cn.wildfirechat.app;


public interface SmsService {
    RestResult.RestCode sendCode(String mobile, String code);
}
