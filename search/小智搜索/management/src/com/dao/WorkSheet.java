package com.dao;

import java.util.HashMap;

public class WorkSheet {
    private String type;
    private String num;
    private HashMap<String, Integer> dataSource = null;
    public WorkSheet(){
        this.dataSource = new HashMap<>();
        for (int i = 1; i < 13; i++) {
            this.dataSource.put(i + "", 0);
        }
    }

    public WorkSheet(HashMap<String,Object> workSheetMap){
        this.type = (String)workSheetMap.get("type");
        this.num = (String)workSheetMap.get("num");
    }

    public HashMap<String, Integer> getDataSource() {
        return dataSource;
    }

    public void setDataSource(HashMap<String, Integer> dataSource) {
        this.dataSource = dataSource;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return "WorkSheet{" +
                "type='" + type + '\'' +
                ", num='" + num + '\'' +
                ", dataSource=" + dataSource +
                '}';
    }
}
