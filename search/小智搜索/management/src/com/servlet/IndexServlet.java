package com.servlet;

import com.common.connection.CFunction;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "IndexServlet")
public class IndexServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        String userID=request.getReader().readLine();
        List<String> list= CFunction.getRight(userID);
        if(list!=null){
            HttpSession session=request.getSession();
            PrintWriter p = response.getWriter();
            System.out.println(list);
            p.println(list.get(0));
            System.out.println(list.get(0));
            p.flush();
            p.close();
            session.setAttribute("userID",userID);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
