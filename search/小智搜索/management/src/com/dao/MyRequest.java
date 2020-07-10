package com.dao;

import java.util.HashMap;
import java.util.Map;

public class MyRequest {
    private final transient String indexName = "question";
    private transient String type;
    private String method;
    private String id;
    private String status;
    private String subType;
    private String mainType;

    public MyRequest(){}

    public MyRequest(Map<String,Object> paraMap){
        this.method = (String) paraMap.get("method");
        this.id = (String) paraMap.get("id");
        this.type = (String) paraMap.get("type");
        this.status = (String) paraMap.get("status");
        this.mainType = (String) paraMap.get("mainType");
        this.subType = (String) paraMap.get("subType");
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
                '}';
    }
}
