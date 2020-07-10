package com.staff.register;

import java.io.*;
import java.util.Random;

public class CsvRegister {
    public static String pwdroute="/home/gly/AAA/pwd.csv";
    /*
    生成随机密码
     */
    public static String RandomPwd(int pwd_len)
    {
        //26个字母+10个数字（0～9）
        final int maxNum=36;
        //生成给随机书数
        int i;
        //生成密码长度
        int count=0;
        char[] str= {'a', 'b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s',
                't','u','v','w','x','y','z','0','1','2','3','4','5','6','7','8','9'};
        StringBuffer pwd=new StringBuffer("");
        Random r=new Random();
        while (count<pwd_len){
            i=Math.abs(r.nextInt(maxNum));
            if(i>=0&&i<str.length){
                pwd.append(str[i]);
                count++;
            }
        }
        return pwd.toString();
    }


    /*
    读取login.csv文件写入pwd.csv中
     */
    public static void readAndWriteCsv()
    {

        try {
            BufferedReader reader = new BufferedReader(new FileReader("/home/gly/AAA/login.csv"));//换成你的文件名
            //reader.readLine();//第一行信息，为标题信息，不用,如果需要，注释掉
            String line = null;

            FileWriter fw=new FileWriter(new File(pwdroute));
            BufferedWriter bfw=new BufferedWriter(fw);
            bfw.write("userID"+","+"userName"+","+"nickName"+","+"passWord"+"\n");
            while((line=reader.readLine())!=null)
            {
                String item[] = line.split(",");//CSV格式文件为逗号分隔符文件，这里根据逗号切分

                //String[] csvHeaders = { "UserName", "Pwd"};
                //csvWriter.writeRecord(csvHeaders);
                for(int i=2;i<item.length;i++)
                {
                    bfw.write(item[i-2]+","+item[i-1]+","+item[i]+",");
                    bfw.write(RandomPwd(6)+"\n");
                    bfw.flush();
                }
            }
            bfw.close();
            System.out.println("success");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        readAndWriteCsv();
    }
}

