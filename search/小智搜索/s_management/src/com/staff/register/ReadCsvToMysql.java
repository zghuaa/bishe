package com.staff.register;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class ReadCsvToMysql {
    private static Connection con;
    public void csvInMysql() throws FileNotFoundException, SQLException {
        File file = new File("/home/gly/AAA/pwd.csv");
        Scanner in = new Scanner(file);

        getConnect();
        System.out.println("数据库连接成功");
        insert_data(in);
        System.out.println("数据导入成功");
    }


    private static void insert_data(Scanner in) throws SQLException
    {
        String sql = "insert into common_management(userID,userName,nickName,passWord)"
                + "values(?,?,?,?)";
        con.setAutoCommit(false);
        PreparedStatement pstmt = con.prepareStatement(sql);
        in.next();
        while (in.hasNext())
        {
            String temp1 = in.nextLine();
            String[] temp = temp1.split(",");

            if (temp.length < 4)
                continue;

            if (temp.length == 4)
            {
                pstmt.setString(1, temp[0]);
                pstmt.setString(2, temp[1]);
                pstmt.setString(3, temp[2]);
                pstmt.setString(4, temp[3]);
            }
            pstmt.addBatch();
            execute(pstmt);
        }
        pstmt.executeBatch();
        con.commit();

        pstmt.close();
        con.close();
    }

    public static int execute(PreparedStatement pstmt) throws SQLException
    {

        pstmt.executeBatch();
        con.commit();
        return 0;

    }

    private static void getConnect()
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/glmd?useUnicode=true&characterEncoding=utf8&useServerPrepStmts=false&rewriteBatchedStatements=true",
                    "root", "123456");
        }
        catch (ClassNotFoundException | SQLException e)
        {
            e.printStackTrace();
        }
    }

}
