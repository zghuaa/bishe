package com.common.connection;

import java.sql.Connection;

public class PooledConnection {
    Connection connection=null;  //数据库连接
    boolean busy=false;         //默认此连接没有正在使用
    public PooledConnection(Connection connection){
        this.connection=connection;
    }
    public Connection getConnection(){
        return connection;
    }
    public void setConnection(Connection connection){
        this.connection=connection;
    }
    public boolean isBusy(){
        return busy;
    }
    public void setBusy(boolean busy){
        this.busy=busy;
    }
}
