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

@WebServlet(name = "/ShenPiFkServlet")

public class ShenPiFkServlet extends HttpServlet {
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
        ElasticSearchOpt elaOpt = new ElasticSearchOpt();
        PrintWriter ptr = resp.getWriter();
        String requestPara = req.getReader().readLine();
        System.out.println(requestPara);
        JSONObject jsonObject = JSON.parseObject(requestPara);
        MyRequest myRequest = new MyRequest(jsonObject.getInnerMap());
        System.out.println(myRequest.toString());
        SearchResponse response = elaOpt.searchFKui(myRequest);
        System.out.println("===================");
        SearchHits hits = response.getHits();
        int count = hits.getHits().length;
        System.out.println(count);
        String json = "[";
        for (int i = 0; i < hits.getHits().length ; i++) {
            SearchHit  searchHit = hits.getHits()[i];
            searchHit.getSource().put("id",searchHit.getId());
            String id = searchHit.getId();
            String date = (String) searchHit.getSource().get("date");
            String mainType = (String) searchHit.getSource().get("mainType");
            String subType = (String) searchHit.getSource().get("subType");
            String email = (String) searchHit.getSource().get("email");
            String tijaoren = (String) searchHit.getSource().get("tijaoren");
            String status = (String) searchHit.getSource().get("status");
            switch (status){
                case "0": status = "未审批";break;
                case "1":status = "通过";break;
                default:status="驳回";break;
            }
            if(i!=hits.getHits().length-1){
                json+="{\"id\":\""+id+"\",";
                json+="\"date\":\""+date+"\",";
                json+="\"mainType\":\""+mainType+"\",";
                json+="\"subType\":\""+subType+"\",";
                json+="\"email\":\""+email+"\",";
                json+="\"tijaoren\":\""+tijaoren+"\",";
                json+="\"status\":\""+status+"\"},";
            }else {
                json += "{\"id\":\"" + id + "\",";
                json += "\"date\":\"" + date + "\",";
                json += "\"mainType\":\"" + mainType + "\",";
                json += "\"subType\":\"" + subType + "\",";
                json += "\"email\":\"" + email + "\",";
                json += "\"tijaoren\":\"" + tijaoren + "\",";
                json += "\"status\":\"" + status + "\"}";
            }
        }
        json+="]";
        String responJson = "{\"code\":0,\"msg\":\"\",\"count\":"+count+",\"data\":"+json+"}";
        ptr.println(responJson);
        ptr.flush();
        ptr.close();
        //{"code":0,"msg":"","count":1000,"data":[{"date":10000,"id":"user-0","yhuser":"女","mainType":"城市-0","subType":"签名-0","email":255,"status":24}]}
    }

}
