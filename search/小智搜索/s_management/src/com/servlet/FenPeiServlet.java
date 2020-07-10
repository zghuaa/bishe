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

@WebServlet(name = "FenPeiServlet")
public class FenPeiServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Access-Control-Allow-Origin", "*");

        String depart=request.getReader().readLine();
        List<String> list= (List<String>) CFunction.find_old_staff(depart);
        if (list!=null){
            HttpSession session=request.getSession();
            PrintWriter p = response.getWriter();
            p.print(list);
            System.out.println(list);
            p.flush();
            p.close();
            session.setAttribute("department",depart);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
