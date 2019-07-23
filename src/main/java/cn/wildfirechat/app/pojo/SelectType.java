package cn.wildfirechat.app.pojo;

import java.util.Date;

public class SelectType {

    private int id;
    //用户id
    private String userId;
    //是否允许手机号查询
    private int cMobile;
    //是否允许群组添加
    private int cGroup;
    //是否允许二维码添加
    private int cQrcode;
    //是否允许名片添加
    private int cCard;
    //创建时间
    private String createTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getcMobile() {
        return cMobile;
    }

    public void setcMobile(int cMobile) {
        this.cMobile = cMobile;
    }

    public int getcGroup() {
        return cGroup;
    }

    public void setcGroup(int cGroup) {
        this.cGroup = cGroup;
    }

    public int getcQrcode() {
        return cQrcode;
    }

    public void setcQrcode(int cQrcode) {
        this.cQrcode = cQrcode;
    }

    public int getcCard() {
        return cCard;
    }

    public void setcCard(int cCard) {
        this.cCard = cCard;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
