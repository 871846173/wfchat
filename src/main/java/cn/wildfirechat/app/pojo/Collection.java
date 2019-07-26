package cn.wildfirechat.app.pojo;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "t_collection")
public class Collection {
    //收藏库
    @Id
    @Column(name = "id")
    private int id;
    private String uid;
    private String mid;

    private String createTime;
//    private String contentType;
//    private String isDelete;

    //消息库
    private String from;
//    private String type;
//    private String target;
//    private String line;
//    private String data;

    private String searchableKey;

    private String sendTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getSearchableKey() {
        return searchableKey;
    }

    public void setSearchableKey(String searchableKey) {
        this.searchableKey = searchableKey;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }
}
