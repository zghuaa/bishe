package com.servlet;

import com.common.connection.CFunction;
import com.common.dao.User_SingleRight;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "SetRightServlet")
public class SetRightServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Access-Control-Allow-Origin", "*");

        String right=request.getReader().readLine();
        String[] rights=right.split(",");
        System.out.println(rights[0]+","+rights[1]+"="+Integer.parseInt(rights[2])+"+"+Integer.parseInt(rights[3])+"-"+Integer.parseInt(rights[4])+"*"+rights[5]);
        CFunction.setRight(rights[0],rights[1],rights[2],rights[3],Integer.parseInt(rights[4]),1,1,2,Integer.parseInt(rights[5]));
//        HttpSession session = request.getSession();
//        PrintWriter p = response.getWriter();
//        p.println("{\"status\":\"1\"}");
//        p.flush();
//        p.close();
//        session.setAttribute("userID", rights[7]);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
