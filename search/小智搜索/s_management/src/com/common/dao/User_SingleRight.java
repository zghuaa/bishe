package com.common.dao;

public class User_SingleRight {
    private String userID;  //工号
    private String userName;   //普通员工姓名
    private String department;  //管理部门
    private String system;  //管理系统
    private int dataInsert;  //数据录入
    private int huizhi;  //客户回执
    private int fankui;  //客户反馈查看
    private int workStatus;    //是否在职   0 离职  1 停职   2 在职
    private int ifDistribution; //是否已分配  1 已分配   0 未分配

    public User_SingleRight(){
        super();
    }
    public User_SingleRight(String userID, String userName, String department, String system, int dataInsert, int huizhi, int fankui, int workStatus, int ifDistribution) {
        this.userID = userID;
        this.userName = userName;
        this.department = department;
        this.system = system;
        this.dataInsert = dataInsert;
        this.huizhi = huizhi;
        this.fankui = fankui;
        this.workStatus = workStatus;
        this.ifDistribution = ifDistribution;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public int getDataInsert() {
        return dataInsert;
    }

    public void setDataInsert(int dataInsert) {
        this.dataInsert = dataInsert;
    }

    public int getHuizhi() {
        return huizhi;
    }

    public void setHuizhi(int huizhi) {
        this.huizhi = huizhi;
    }

    public int getFankui() {
        return fankui;
    }

    public void setFankui(int fankui) {
        this.fankui = fankui;
    }


    public int getWorkStatus() {
        return workStatus;
    }

    public void setWorkStatus(int workStatus) {
        this.workStatus = workStatus;
    }

    public int getIfDistribution() {
        return ifDistribution;
    }

    public void setIfDistribution(int ifDistribution) {
        this.ifDistribution = ifDistribution;
    }

    @Override
    public String toString() {
        return "{" +
                "\"userName\":" +"\""+ userName+"\"" +
                ",\"userID\":"+"\"" + userID +"\""+
                ",\"department\":"+"\"" + department+"\"" +
                ",\"system\":" +"\""+ system +"\""+
                ",\"dataInsert\":" + dataInsert +
                ",\"huizhi\":"+ huizhi+
                ",\"fankui\":"+ fankui+
                ",\"workStatus\":" + workStatus +
                ",\"ifDistribution\":" + ifDistribution +
                '}';
    }
}
