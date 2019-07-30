package cn.wildfirechat.app.pojo;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.soap.Text;
import java.sql.Blob;

@Table(name = "t_message_X")
public class Message {

    @Id
    @Column(name = "id")
    private int id;
    private int mid;
    private String from;
    private int type;
    private String target;
    private int line;
    private Blob data;
    private Text searchableKey;
    private String dt;
    private int contentType;
    private int read;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public Blob getData() {
        return data;
    }

    public void setData(Blob data) {
        this.data = data;
    }

    public Text getSearchableKey() {
        return searchableKey;
    }

    public void setSearchableKey(Text searchableKey) {
        this.searchableKey = searchableKey;
    }

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

    public int getContentType() {
        return contentType;
    }

    public void setContentType(int contentType) {
        this.contentType = contentType;
    }

    public int getRead() {
        return read;
    }

    public void setRead(int read) {
        this.read = read;
    }
}
