package com.servlet;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bulk.ElasticSearchOpt;
import com.bulk.IElasticSearchOpt.IElasticSearchOpt;
import com.dao.MyRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHits;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.util.HashMap;


@WebServlet(name = "NSQServlet")
public class NSQServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("*/*");
        resp.setHeader("Access-Control-Allow-Origin", "*");
        String requestPara = req.getReader().readLine();
        PrintWriter ptr= resp.getWriter();
        System.out.println(requestPara);
        JSONObject jsonObject = JSON.parseObject(requestPara);
        System.out.println(jsonObject+"***********************************");
        MyRequest myRequest = new MyRequest(jsonObject.getInnerMap());
        System.out.println(myRequest.toString()+"$$$$$$$$$$$$$$$$$$$$$$$$$");
        ElasticSearchOpt elaOpt = new ElasticSearchOpt();
//        SearchResponse response = elaOpt.searchFKui(myRequest);
//        SearchHits hits = response.getHits();
        Class elaInf = null;
        String responJson = null;
        try {
            elaInf = Class.forName("com.bulk.ElasticSearchOpt");
            IElasticSearchOpt iElaOpt = (IElasticSearchOpt) elaInf.newInstance();
            System.out.println(myRequest.toString());
            Method elaMethod = elaInf.getMethod(myRequest.getMethod(),MyRequest.class);
            responJson = (String) elaMethod.invoke(iElaOpt,myRequest);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        System.out.println(responJson+"======================");
        ptr.println(responJson);
        ptr.flush();
        ptr.close();
    }
}
