package com.staff.right.select_right;

import com.common.connection.CFunction;
import com.common.dao.Userlogin;

public class Test {
    public static void main(String[] args) throws Exception {
       // CFunction userDao  = new CFunction();
        String userID = "111";
        String passWord = "123";
        Userlogin login= CFunction.s_findUnamePwd(userID,passWord);
        System.out.println(login);
    }
}
