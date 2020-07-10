package com.common.connection;

import java.io.FileInputStream;
import java.sql.*;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

public class ConnectionPool {
        private String driver;
        private String url;
        private String user;
        private String pass;
        private int initialConnections;   //连接池的初始大小,80
        private int increamentConnections;     //连接池自动增加的大小,5
        private int maxConnections;          //连接池最大的大小,120
        private Vector connections=null;      //存放连接池中数据库连接的向量，初始值为null
        private String testTable="";          //测试连接是否可用的测量表明，默认没有测量表

        public void initparam(String paramfile) throws Exception {
            Properties prop=new Properties();
            prop.load(new FileInputStream(paramfile));
            driver=prop.getProperty("driver");
            url=prop.getProperty("url");
            user=prop.getProperty("user");
            pass=prop.getProperty("pass");
            initialConnections= Integer.parseInt(prop.getProperty("initialConnections"));
            increamentConnections= Integer.parseInt(prop.getProperty("increamentConnections"));
            maxConnections= Integer.parseInt(prop.getProperty("maxConnections"));

        }

        public synchronized void createPool() throws Exception {
            //确保连接池没有创建
            if(connections!=null){
                return;
            }
            //实例化JDBC Driver中指定的驱动类实例
            Driver jdbcdriver = (Driver) (Class.forName(driver).newInstance());
            //注册JDBC驱动程序
            DriverManager.registerDriver(jdbcdriver);
            //创建保存连接的向量，初始值为0
            connections=new Vector();
            //根据初始值设置连接
            createConnections(initialConnections);
            System.out.println("数据库连接池创建成功!");
        }

        public void createConnections(int numConnections) throws SQLException {
            for(int x=0;x<numConnections;x++) {
                //判断连接池中的数据库连接数量是否已达到最大，假如maxConnections为0或负数，表示连接数量没有限制
                //假如连接数已达到最大，则退出
                if (maxConnections > 0 && connections.size() >= maxConnections) {
                    break;
                }
                try {
                    connections.addElement(new PooledConnection(newConnection()));
                } catch (SQLException e) {
                    System.out.println("创建数据库连接失败!"+e.getMessage());
                    throw new SQLException();
                }
                System.out.println("数据库连接已创建");
            }
        }

        private Connection newConnection() throws SQLException {
            //创建一个数据库连接
            Connection conn=DriverManager.getConnection(url,user,pass);
            //假如这是第一次创建数据库连接，获得此数据库答应支持的最大客户连接数目driverMaxConnections
            if(connections.size()==0){
                DatabaseMetaData metaData=conn.getMetaData();
                int driverMaxConnections=metaData.getMaxConnections();
                //假如连接池中设置的最大连接数量大于数据库答应的连接数目，则设置连接池的最大连接数目为数据库答应的最大数目
                if(driverMaxConnections>0&&maxConnections>driverMaxConnections){
                    maxConnections=driverMaxConnections;
                }
            }
            return conn;
        }

        public synchronized Connection getConnection() throws InterruptedException, SQLException {
            //确保连接池已被创建
            if(connections==null){
                return null;
            }
            //获得一个可用的数据库连接
            Connection conn=getFreeConnection();
            //假如目前没有可以使用的连接，则等一会儿再试
            while (conn==null){
                wait(250);
                conn=getFreeConnection();
            }
            //返回创建的新的数据库连接
            return conn;
        }

        private Connection getFreeConnection() throws SQLException {
            Connection conn=findFreeConnection();
            //假如目前连接池中没有可用连接，则创建一些连接
            if(conn==null){
                createConnections(increamentConnections);
                //重新从连接池中查找是否有可用的连接，如果还是没有可用连接，则返回null
                conn=findFreeConnection();
                if(conn==null){
                    return null;
                }
            }
            return conn;
        }

        private Connection findFreeConnection(){
            Connection conn=null;
            PooledConnection pooc=null;
            //获得连接池向量中的所有对象
            Enumeration enumeration=connections.elements();
            //遍历所有的对象，看是否有可用的连接
            while(enumeration.hasMoreElements()){
                pooc= (PooledConnection) enumeration.nextElement();
                //假如不忙，获得此数据库连接，并设置为忙
                if(!pooc.isBusy()){
                    conn=pooc.getConnection();
                    pooc.setBusy(true);
                    //测试连接是否可用，假如不可用则创建一个新的连接，并替换此不可用的连接对象，假如创建失败，返回null
                    if(!testConnection(conn)){
                        try {
                            conn=newConnection();
                        } catch (SQLException e) {
                            e.printStackTrace();
                            System.out.println("创建数据库连接失败! "+e.getMessage());
                            return null;
                        }
                        pooc.setConnection(conn);
                    }
                    break;
                }
            }
            return conn;  //返回找到的可用连接
        }

        private boolean testConnection(Connection conn){
            try{
                //如果测试表为空，则试着使用此连接的setAutoCommit（）方法来判定连接是否可用
                if(testTable.equals(" ")){
                    conn.setAutoCommit(true);
                }else{
                    //有测试表使用测试表测试
                    Statement statement=conn.createStatement();
                    statement.execute("select count(*) from "+testTable);
                }
            }catch(SQLException e){
                closeConnection(conn);
                return false;
            }
            return true;
        }

        private void closeConnection(Connection conn){
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println();
            }
        }

    public void returnConnection(Connection conn) {
        // 确保连接池存在，假如连接没有创建（不存在），直接返回
        if (connections == null) {
            System.out.println(" 连接池不存在，无法返回此连接到连接池中 !");
            return;
        }
        PooledConnection pConn = null;
        Enumeration enumerate = connections.elements();
        // 遍历连接池中的所有连接，找到这个要返回的连接对象
        while (enumerate.hasMoreElements()) {
            pConn = (PooledConnection) enumerate.nextElement();
            // 先找到连接池中的要返回的连接对象
            if (conn == pConn.getConnection()) {
                // 找到了 , 设置此连接为空闲状态
                pConn.setBusy(false);
                break;
            }
        }
    }

        private void wait(int mSecond) {
            try {
                Thread.sleep(mSecond);
            } catch (InterruptedException e) {
            }
        }

}
