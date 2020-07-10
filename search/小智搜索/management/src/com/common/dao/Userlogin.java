package com.common.dao;

public class Userlogin {
    private String userID;
    private String passWord;
    private int status;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Userlogin{" +
                "userID='" + userID + '\'' +
                ", passWord='" + passWord + '\'' +
                ", status=" + status +
                '}';
    }
}
