package com.common.connection;

import com.common.dao.Userlogin;
import com.common.dao.User_SingleRight;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 2、超级管理员登录账户名密码
 * 3、找未分配权限的管理员
 * 4、普通管理员账户冻结，即离职
 * 5、超级管理员为普通管理员设置权限
 * 6、从已分配人员里找对应部门的人员
 * 7、
 */
public class CFunction {
        private static Userlogin user=null;
        private static User_SingleRight usersr=null;
        private static User_SingleRight user_singleRight=null;

        private static PreparedStatement psta=null;
        private static Connection conn=null;
        private static ResultSet rs=null;

        private static String  sqldelete="delete from common_management where userID=?";  //离职
        private static String sqlLogin="select userID,passWord from super_management where userID=? and passWord=?";  //登录
        private static String sqlRight="insert into name_single_right values(?,?,?,?,?,?,?,?,?,?,?)";  //设置权限
        private static String sqlDepartment="select * from common_management where department=?";  //查看本部门已分配人员
        private static String aqlDistribution="select * from name_single_right where ifDistribution=0";  //0代表未分配
        private static String sqlCsvInsertcm="insert into common_management(userID,userName,nickName,passWord)values(?,?,?,?)";
        private static String sqlCsvInsertcl="insert into clogin_management(userID,passWord)values(?,?)";
        private static String sqlCsvInsertri="insert into name_single_right(userID,userName)values(?,?)";



        //2、超级管理员登录账户名密码
        public static Userlogin s_findUnamePwd(String userID, String passWord){
        conn= Conn.getConnection();
            try {
                psta = conn.prepareStatement(sqlLogin);
                psta.setString(1, userID);
                psta.setString(2, passWord);
                ResultSet rs = psta.executeQuery();
                while (rs.next())
                {
                    user = new Userlogin();
                    user.setUserID(rs.getString("userID"));
                    user.setPassWord(rs.getString("passWord"));
                }
        } catch (SQLException e) {
                e.printStackTrace();
        }finally {
                try {
                    if(psta!=null)psta.close();
                    if(conn!=null)conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        return user;
    }

        //3、找未分配权限的管理员
        public static List<String> getSelect(){
        List<String> list=new ArrayList<String>();
        try {
            conn= Conn.getConnection();
            psta=conn.prepareStatement(aqlDistribution);
            rs = psta.executeQuery();
            while (rs.next())
            {
                list.add(rs.getString("userName"));
                list.add(rs.getString("userID"));
                list.add(rs.getString("department"));
                list.add(rs.getString("system"));
                list.add(rs.getString("dataInsert"));
                list.add(rs.getString("huizhi"));
                list.add(rs.getString("fankui"));
                list.add(rs.getString("workStatus"));
                list.add(rs.getString("ifDistribution"));
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
        }
        return list;
    }

        //4、普通管理员账户冻结，即离职
        public static void deleteUID(String userID) throws Exception {
            try
            {
                conn= Conn.getConnection();
                psta=conn.prepareStatement(sqldelete);
                psta.setString(1,userID);
                psta.executeUpdate();
            }catch (SQLException ex1)
            {
                ex1.printStackTrace();
            }finally
            {
                psta.close();
            }
        }

        //5、超级管理员为普通管理员设置权限
        public static void setRight(String userID,String userName,String department,String system,int dataInsert,int huizhi,int fankui,int workStatus,int ifDistribution) {
            try
            {
                conn= Conn.getConnection();
                psta=conn.prepareStatement(sqlRight);
                psta.setString(1,userID);
                psta.setString(2,userName);
                psta.setString(3,department);
                psta.setString(4,system);
                psta.setInt(5,dataInsert);
                psta.setInt(6,huizhi);
                psta.setInt(7,fankui);
                psta.setInt(8,workStatus);
                psta.setInt(9,ifDistribution);
                psta.executeUpdate();
            } catch (Exception e)
            {
                e.printStackTrace();
            }finally
            {
                try
                {
                    psta.close();
                } catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }

        }

        //6、从已分配人员里找对应部门的人员
        public static User_SingleRight find_old_staff(String department) {
            try
            {
                conn= Conn.getConnection();
                psta=conn.prepareStatement(sqlDepartment);
                psta.setString(1,department);
                rs = psta.executeQuery();
                while (rs.next())
                {
                    usersr = new User_SingleRight();
                    usersr.setUserName(rs.getString("userName"));
                    usersr.setUserID(rs.getString("userID"));
                    usersr.setDepartment(rs.getString("department"));
                    usersr.setSystem(rs.getString("system"));
                    usersr.setDataInsert(rs.getInt("dataInsert"));
                    usersr.setHuizhi(rs.getInt("huizhi"));
                    usersr.setFankui(rs.getInt("fankui"));
                    usersr.setWorkStatus(rs.getInt("workStatus"));
                    usersr.setIfDistribution(rs.getInt("ifDistribution"));
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
                return usersr;
            }

        }

        //超级管理员将管理员信息入common库
        public static void csvInMysql() {
        try {
            conn= Conn.getConnection();
            conn.setAutoCommit(false);
            String route = "D:\\毕设\\AAA\\login.csv";
            File file = new File(route);
            Scanner in = new Scanner(file);
            psta=conn.prepareStatement(sqlCsvInsertcm);
            in.next();
            while (in.hasNext())
            {
                String temp1 = in.nextLine();
                String[] temp = temp1.split(",");

                if (temp.length < 4)
                    continue;

                if (temp.length == 4)
                {
                    psta.setString(1, temp[0]);
                    psta.setString(2, temp[1]);
                    psta.setString(3, temp[2]);
                    psta.setString(4, temp[3]);
                }
                psta.addBatch();
                psta.executeBatch();
                conn.commit();
            }
            psta.executeBatch();
            conn.commit();

            psta.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

        //超级管理员将管理员信息入clogin库
        public static void csvInMysqlLogin(){
        try {
            conn= Conn.getConnection();
            conn.setAutoCommit(false);
            File file = new File("D:\\毕设\\AAA\\pwd.csv");
            Scanner in = new Scanner(file);
            psta=conn.prepareStatement(sqlCsvInsertcl);
            psta=conn.prepareStatement(sqlCsvInsertri);
            in.next();
            while (in.hasNext())
            {
                String temp1 = in.nextLine();
                String[] temp = temp1.split(",");

                if (temp.length < 4)
                    continue;

                if (temp.length == 4)
                {
                    psta.setString(1, temp[0]);
                    psta.setString(2, temp[3]);
                }
                psta.addBatch();
                psta.executeBatch();
                conn.commit();
            }
            psta.executeBatch();
            conn.commit();

            psta.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static void csvInMysqlRight(){
        try {
            conn= Conn.getConnection();
            conn.setAutoCommit(false);
            File file = new File("D:\\毕设\\AAA\\pwd.csv");
            Scanner in = new Scanner(file);
            psta=conn.prepareStatement(sqlCsvInsertri);
            in.next();
            while (in.hasNext())
            {
                String temp1 = in.nextLine();
                String[] temp = temp1.split(",");

                if (temp.length < 4)
                    continue;

                if (temp.length == 4)
                {
                    psta.setString(1, temp[0]);
                    psta.setString(2, temp[1]);
                }
                psta.addBatch();
                psta.executeBatch();
                conn.commit();
            }
            psta.executeBatch();
            conn.commit();

            psta.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }




    public static void main(String[] args) throws Exception {
            CFunction rightst=new CFunction();
            //rightst.deleteUname("aa");
           // rightst.setRight("gao","100","gl","xt",1,1,1,1,1,2,0);
           // CFunction.find_old_staff("hl");
//            List<String> list=CFunction.getSelect();
//            for (int i=0;i<list.size();i++){
//                System.out.println(list.get(i));
//            }
        System.out.println(rightst.getSelect());

        }


}
