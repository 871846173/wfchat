package cn.wildfirechat.app.pojo;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "t_user")
public class User {
    @Id
    @Column(name = "id")
    private int id;
    private String userId;
    private String mobile;
    private String passwdmd5;

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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPasswdmd5() {
        return passwdmd5;
    }

    public void setPasswdmd5(String passwdmd5) {
        this.passwdmd5 = passwdmd5;
    }
}
