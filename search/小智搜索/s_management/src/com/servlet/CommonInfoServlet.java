package com.servlet;

import com.staff.register.CsvRegister;
import com.staff.register.CsvToJson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;


//
@WebServlet(name = "CommonInfoServlet")
public class CommonInfoServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws  IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        String info=CsvToJson.getJsonFromFile(CsvRegister.pwdroute,",");
        if(info!=null){
            //HttpSession session = request.getSession();
            PrintWriter p = response.getWriter();
            p.print(info);
            p.flush();
            p.close();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
