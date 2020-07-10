package com.dao;



import org.apache.log4j.Logger;

import java.util.Map;

public class Information {

    private static Logger logger = Logger.getLogger(Information.class);
    private transient final String indexName = "information";
    private transient String type;
    private transient String id;
    private transient int currentPage;
    private String tag;
    private String mainType;
    private String subType;
    private String title;
    private String content;
    private Integer score;
    private String serviceType;
    private String date;

    public Information(){}

    public Information(Map<String, Object> informationMap) {
        this.type = (String) informationMap.get("type");
        this.title = (String) informationMap.get("title");
        this.content = (String) informationMap.get("content");
        this.id = (String) informationMap.get("id");
        this.tag = (String) informationMap.get("tag");
        this.mainType = (String) informationMap.get("mainType");
        this.subType = (String) informationMap.get("subType");
        this.serviceType = (String) informationMap.get("serviceType");
        this.date = (String) informationMap.get("date");
        if (informationMap.get("score") == null) {
            this.score = 0;
        } else {
            try {
                this.score = (Integer) informationMap.get("score");
            } catch (Exception e) {
                logger.error("cast number error:  informationMap.get(\"score\"):\t" + informationMap.get("score"), e);
            }
        }
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
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

    @Override
    public String toString() {
        return "Information{" +
                "indexName='" + indexName + '\'' +
                ", type='" + type + '\'' +
                ", id='" + id + '\'' +
                ", tag='" + tag + '\'' +
                ", mainType='" + mainType + '\'' +
                ", subType='" + subType + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", score=" + score +
                ", currentPage=" + currentPage +
                ", serviceType='" + serviceType + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
