package com.servlet;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bulk.ElasticSearchOpt;
import com.dao.MyRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

@WebServlet(name = "NHuiDaServlet")
public class NHuiDaServlet extends HttpServlet {
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
        HashMap<String,String> paraMap = new HashMap<>();
        ElasticSearchOpt elaOpt = new ElasticSearchOpt();
        JSONObject jsonObject = JSON.parseObject(requestPara);
        System.out.println(paraMap);
        MyRequest myRequest = new MyRequest(jsonObject.getInnerMap());
        SearchResponse response = elaOpt.searchFKui(myRequest);
        SearchHits hits = response.getHits();
        int count = hits.getHits().length;
        String json = "[";
        for (int i = 0; i < hits.getHits().length ; i++) {
            SearchHit searchHit = hits.getHits()[i];
            searchHit.getSource().put("id",searchHit.getId());
            String id = searchHit.getId();
            String date = (String) searchHit.getSource().get("date");
            String subType = (String) searchHit.getSource().get("subType");
            String email = (String) searchHit.getSource().get("email");
            String status = (String) searchHit.getSource().get("status");
            if(i!=hits.getHits().length-1){
                json+="{\"id\":\""+id+"\",";
                json+="\"date\":\""+date+"\",";
                json+="\"subType\":\""+subType+"\",";
                json+="\"email\":\""+email+"\",";
                json+="\"status\":\""+status+"\"},";
            }else {
                json += "{\"id\":\"" + id + "\",";
                json += "\"date\":\"" + date + "\",";
                json += "\"subType\":\"" + subType + "\",";
                json += "\"email\":\"" + email + "\",";
                json += "\"status\":\"" + status + "\"}";
            }
        }
        json+="]";
        String responJson = "{\"code\":0,\"msg\":\"\",\"count\":"+count+",\"data\":"+json+"}";
        System.out.println(responJson);
        ptr.println(responJson);
        ptr.flush();
        ptr.close();
    }

}
