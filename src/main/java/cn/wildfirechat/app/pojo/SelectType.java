package cn.wildfirechat.app.pojo;

public class SelectType {

    private int id;
    //用户id
    private String userId;
    //是否允许手机号查询
    private String cMobile;
    //是否允许群组添加
    private String cGroup;
    //是否允许二维码添加
    private String cQrcode;
    //是否允许名片添加
    private String cCard;
    //创建时间
    private String createTime;

    private String mobile;

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

    public String getcMobile() {
        return cMobile;
    }

    public void setcMobile(String cMobile) {
        this.cMobile = cMobile;
    }

    public String getcGroup() {
        return cGroup;
    }

    public void setcGroup(String cGroup) {
        this.cGroup = cGroup;
    }

    public String getcQrcode() {
        return cQrcode;
    }

    public void setcQrcode(String cQrcode) {
        this.cQrcode = cQrcode;
    }

    public String getcCard() {
        return cCard;
    }

    public void setcCard(String cCard) {
        this.cCard = cCard;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
