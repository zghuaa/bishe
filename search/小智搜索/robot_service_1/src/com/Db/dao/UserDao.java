package com.Db.dao;
import java.util.Map;

public class UserDao {
    private transient final String indexName = "information";
    private transient final String type = "user";
    private transient String flag = "";
    private String createDate;
    private String email;
    private String pass;

    public UserDao(){}

    public UserDao(Map<String,Object> userMap){
        this.email = (String) userMap.get("email");
        this.pass = (String) userMap.get("pass");
        this.flag = (String) userMap.get("flag");
        this.createDate = (String) userMap.get("createDate");
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getIndexName() {
        return indexName;
    }

    public String getType() {
        return type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    @Override
    public String toString() {
        return "UserDao{" +
                "indexName='" + indexName + '\'' +
                ", type='" + type + '\'' +
                ", flag='" + flag + '\'' +
                ", createDate='" + createDate + '\'' +
                ", email='" + email + '\'' +
                ", pass='" + pass + '\'' +
                '}';
    }
}
