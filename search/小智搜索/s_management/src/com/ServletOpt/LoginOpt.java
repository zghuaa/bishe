package com.ServletOpt;

import com.common.connection.CFunction;
import com.common.dao.Userlogin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class LoginOpt {
    HttpServletRequest request;
    HttpServletResponse response;
    public LoginOpt(){
        super();
    }

    public LoginOpt(HttpServletRequest request,HttpServletResponse response) throws IOException {
        this.request = request;
        this.response=response;
    }

    public void sLogin() throws Exception {
        String user1 = this.request.getReader().readLine();
        String[] up = user1.split(",");
        Userlogin user = null;
        user = CFunction.s_findUnamePwd(up[0], up[1]);
        if (user != null) {
            HttpSession session = this.request.getSession();
            PrintWriter p = this.response.getWriter();
            //{"status":"1"}
            p.println("{\"status\":\"1\"}");
            p.flush();
            p.close();
            session.setAttribute("userID", up[0]);
//            this.response.sendRedirect("/index.html");
        } else {
            this.request.setAttribute("error", "您的账号和密码不匹配，请重新输入");
            this.request.getRequestDispatcher("/index.html").forward(this.request, this.response);
        }
    }


}
