package com.Db.dao;

import java.util.Map;

public class Question {
    private transient final String indexName = "question";
    private transient String type;
    private String email;
    private String title;
    private String question;
    private String createDate;
    private String date;
    public Question(){}

    public Question(Map<String,Object> questionMap){
        this.email = (String) questionMap.get("email");
        this.type = (String) questionMap.get("type");
        this.title = (String) questionMap.get("title");
        this.question = (String) questionMap.get("content");
        this.question = (String) questionMap.get("date");
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getIndexName() {
        return indexName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "Question{" +
                "indexName='" + indexName + '\'' +
                ", type='" + type + '\'' +
                ", email='" + email + '\'' +
                ", title='" + title + '\'' +
                ", question='" + question + '\'' +
                ", createDate='" + createDate + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
