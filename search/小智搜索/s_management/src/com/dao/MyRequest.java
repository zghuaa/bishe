package com.dao;

import java.util.HashMap;
import java.util.Map;

public class MyRequest {
    private transient String indexName = "question";
    private transient String type;
    private String method;
    private String id;
    private String status;
    private String subType;
    private String mainType;
    private String date;
    private String title;
    private String content;
    private String answer;

    public MyRequest(){}

    public MyRequest(Map<String,Object> paraMap){
        this.method = (String) paraMap.get("method");
        this.id = (String) paraMap.get("id");
        this.type = (String) paraMap.get("type");
        this.status = (String) paraMap.get("status");
        this.mainType = (String) paraMap.get("mainType");
        this.subType = (String) paraMap.get("subType");
        this.date = (String)paraMap.get("date");
        this.title = (String)paraMap.get("title");
        this.answer = (String)paraMap.get("answer");
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIndexName() {
        return indexName;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public String getMainType() {
        return mainType;
    }

    public void setMainType(String mainType) {
        this.mainType = mainType;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "MyRequest{" +
                "indexName='" + indexName + '\'' +
                ", type='" + type + '\'' +
                ", method='" + method + '\'' +
                ", id='" + id + '\'' +
                ", status='" + status + '\'' +
                ", subType='" + subType + '\'' +
                ", mainType='" + mainType + '\'' +
                ", date='" + date + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", answer='" + answer + '\'' +
                '}';
    }
}
