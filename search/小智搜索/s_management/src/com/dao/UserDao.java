package com.dao;
import java.util.Map;

public class UserDao {
    private transient final String indexName = "information";
    private transient String type = "user";
    private transient String flag ;
    private transient String randomCode;
    private String date;
    private String email;
    private String pass;

    public UserDao() {
    }

    public UserDao(Map<String, Object> userMap) {
        this.email = (String) userMap.get("email");
        this.pass = (String) userMap.get("pass");
//        this.type = (String) userMap.get("type");
        this.flag = (String) userMap.get("flag");
        this.date = (String) userMap.get("date");
        this.randomCode = (String) userMap.get("randomcode");
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
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

    public String getRandomCode() {
        return randomCode;
    }

    public void setRandomCode(String randomCode) {
        this.randomCode = randomCode;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "UserDao{" +
                "indexName='" + indexName + '\'' +
                ", type='" + type + '\'' +
                ", flag='" + flag + '\'' +
                ", randomCode='" + randomCode + '\'' +
                ", date='" + date + '\'' +
                ", email='" + email + '\'' +
                ", pass='" + pass + '\'' +
                '}';
    }
}
