package com.common.connection;

import com.common.dao.Userlogin;
import com.common.dao.User_SingleRight;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CFunction {
        private static Userlogin user=null;
        private static User_SingleRight usersr=null;
        private static String route="D:\\bishe\\management\\src\\com\\common\\jdbc.properties";

        private static ConnectionPool conpool=new ConnectionPool();
        private static PreparedStatement psta=null;
        private static Connection conn=null;
        private static ResultSet rs=null;

        private static String sqlWorking="select workStatus from name_single_right where userID=?";
        private static String sqlLogin="select userID,passWord from common_management where userID=? and passWord=?";  //普通管理员登录
        private static String sqlGetRight="select userName,department,system,dataInsert,huizhi,fankui from name_single_right where userID=?";

        public static void initConnectionPool() throws Exception {

            conpool.initparam(route);
            try {
                conpool.createPool();
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }




        //普通管理员在职状态
        public static int  ifWorking(String userID){
            int workStatus=0;
        try {
            initConnectionPool();
            conn=conpool.getConnection();
            psta=conn.prepareStatement(sqlWorking);
            psta.setString(1,userID);
            rs = psta.executeQuery();
            rs.absolute(1);
            workStatus=rs.getInt("workStatus");
            System.out.println(workStatus);

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                psta.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            conpool.returnConnection(conn);
        }

            return workStatus;
    }


       //普通管理员登录账户名密码
       public static Userlogin s_findUnamePwd(String userID, String passWord) throws Exception {
        initConnectionPool();
        conn=conpool.getConnection();
        psta = conn.prepareStatement(sqlLogin);
        psta.setString(1, userID);
        psta.setString(2, passWord);
        rs = psta.executeQuery();
        while (rs.next()) {
            user = new Userlogin();
            user.setUserID(rs.getString("userID"));
            user.setPassWord(rs.getString("passWord"));
        }
        rs.close();
        psta.close();
        conpool.returnConnection(conn);
        return user;
    }


       public static List<String> getRight(String userID){
           List<String> list=new ArrayList<String>();
           try {
               initConnectionPool();
               conn=conpool.getConnection();
               psta = conn.prepareStatement(sqlGetRight);
               psta.setString(1,userID);
               rs = psta.executeQuery();
               while (rs.next()){
                   list.add("{\"userName\":"+"\""+rs.getString("userName")+"\""
                           +",\"department\":"+"\""+rs.getString("department")+"\""
                           +",\"system\":"+"\""+rs.getString("system")+"\""
                           +",\"dataInsert\":"+rs.getString("dataInsert")
                           +",\"huizhi\":"+rs.getString("huizhi")
                           +",\"fankui\":"+rs.getString("fankui")
                           +"}"
                   );

               }

           } catch (Exception e) {
               e.printStackTrace();
           }finally {
               try {
                   rs.close();
               } catch (SQLException e) {
                   e.printStackTrace();
               }
               try {
                   psta.close();
               } catch (SQLException e) {
                   e.printStackTrace();
               }
               conpool.returnConnection(conn);
           }
           return list;
    }



        public static void main(String[] args) throws Exception {
           // CFunction rightst=new CFunction();
            //rightst.deleteUname("aa");
           // rightst.setRight("zhou","0001","gl","xt",1,1,1,1,1,1,1);
            System.out.println(CFunction.ifWorking("100"));
        }

}
