package cn.wildfirechat.app;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ConfigurationProperties(prefix = "sms")
@PropertySource(value = "file:config/tencent_sms.properties")
public class TencentSMSConfig {
    String appid;
    //    String appkey;
    String serverip;
    String serverport;
    String accountsid;
    String accounttoken;
    String templateId;
    String superCode;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getTemplateId() {
        return templateId;
    }

    public String getSuperCode() {
        return superCode;
    }

    public void setSuperCode(String superCode) {
        this.superCode = superCode;
    }

    public String getServerip() {
        return serverip;
    }

    public void setServerip(String serverip) {
        this.serverip = serverip;
    }

    public String getServerport() {
        return serverport;
    }

    public void setServerport(String serverport) {
        this.serverport = serverport;
    }

    public String getAccountsid() {
        return accountsid;
    }

    public void setAccountsid(String accountsid) {
        this.accountsid = accountsid;
    }

    public String getAccounttoken() {
        return accounttoken;
    }

    public void setAccounttoken(String accounttoken) {
        this.accounttoken = accounttoken;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }
}
