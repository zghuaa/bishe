package com.common.dao;

public class Userlogin {
    private String userID;
    private String passWord;

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

    @Override
    public String toString() {
        return "Userlogin{" +
                "userID='" + userID + '\'' +
                ", passWord='" + passWord + '\'' +
                '}';
    }
}
