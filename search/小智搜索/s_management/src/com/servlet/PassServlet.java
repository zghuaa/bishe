package com.servlet;

import com.bulk.ElasticSearchOpt;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "PassServlet")
public class PassServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        request.setCharacterEncoding("utf-8");
        response.setContentType("*/*");
        response.setHeader("Access-Control-Allow-Origin", "*");
        String repuest = request.getReader().readLine();
        ElasticSearchOpt elaOpt = new ElasticSearchOpt();
        //elaOpt.addJson(repuest);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
