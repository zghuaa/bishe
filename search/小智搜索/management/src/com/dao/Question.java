package com.dao;

import java.util.Map;

public class Question {
    private transient final String indexName = "question";
    private transient String type ="fankui";
    private String email;
    private String title;
    private String answer;
    private String question;
    private String date;//用户提交问题时间
    private String mainType;
    private String subType;
    private String status;
    private String tijaoren;//普通管理员
    private String tijiaoshijian;//普通管理员提交时间
    private String shenpishijian;//超级管理员提交时间
    public Question(){}
//{"mainType":"department","subType":"system","date":"date","question":"question","answer":"answer","email":"email","title":"title","tijaoren":"tijaoren","tijiaoshijian":"tijiaoshijian","shenpishijian":"shenpishijian","status","status"}
    public Question(Map<String,Object> questionMap){
        this.email = (String) questionMap.get("email");
        this.title = (String) questionMap.get("title");
        this.question = (String) questionMap.get("question");
        this.date = (String) questionMap.get("date");
        this.status = (String)questionMap.get("status");
        this.mainType = (String)questionMap.get("mainType");
        this.subType = (String) questionMap.get("subType");
        this.tijaoren = (String)questionMap.get("tijaoren");
        this.tijiaoshijian = (String)questionMap.get("tijiaoshijian");
        this.shenpishijian = (String)questionMap.get("shenpishijian");
        this.answer = (String)questionMap.get("answer");
    }

    public String getIndexName() {
        return indexName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMainType() {
        return mainType;
    }

    public void setMainType(String mainType) {
        this.mainType = mainType;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTijaoren() {
        return tijaoren;
    }

    public void setTijaoren(String tijaoren) {
        this.tijaoren = tijaoren;
    }

    public String getTijiaoshijian() {
        return tijiaoshijian;
    }

    public void setTijiaoshijian(String tijiaoshijian) {
        this.tijiaoshijian = tijiaoshijian;
    }

    public String getShenpishijian() {
        return shenpishijian;
    }

    public void setShenpishijian(String shenpishijian) {
        this.shenpishijian = shenpishijian;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "Question{" +
                "indexName='" + indexName + '\'' +
                ", type='" + type + '\'' +
                ", email='" + email + '\'' +
                ", title='" + title + '\'' +
                ", answer='" + answer + '\'' +
                ", question='" + question + '\'' +
                ", date='" + date + '\'' +
                ", mainType='" + mainType + '\'' +
                ", subType='" + subType + '\'' +
                ", status='" + status + '\'' +
                ", tijaoren='" + tijaoren + '\'' +
                ", tijiaoshijian='" + tijiaoshijian + '\'' +
                ", shenpishijian='" + shenpishijian + '\'' +
                '}';
    }
}
