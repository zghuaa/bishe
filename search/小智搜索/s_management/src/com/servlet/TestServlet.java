package com.servlet;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bulk.ElasticSearchOpt;
import com.dao.Information;
import com.dao.WorkSheet;
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

@WebServlet(name = "TestServlet")
public class TestServlet extends HttpServlet {
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
        String msg = req.getReader().readLine();
        JSONObject o = JSON.parseObject(msg);
        System.out.println(o.getInnerMap());
        ElasticSearchOpt elasticSearchOpt = new ElasticSearchOpt();
        Information in = new Information(o.getInnerMap());
        //      type           date   clickNum
        HashMap<String, HashMap<String, Integer>> workSheeter = new HashMap<>();
        SearchHits searchHits = elasticSearchOpt.searchByDate(in);
        //      date   clickNum

        for (SearchHit hitFields : searchHits) {
            workSheeter.put(hitFields.getType(), new WorkSheet().getDataSource());
        }
//        System.out.println(workSheeter);
        System.out.println(workSheeter);
        for (SearchHit hit : searchHits) {
            String type = hit.getType();
            String date = (String) hit.getSource().get("date");
            hit.getSource().put("year", date.substring(0, 4));
            hit.getSource().put("month", date.substring(5, 7));
            hit.getSource().put("day", date.substring(8, 10));
            String month = (String) hit.getSource().get("month");
            HashMap<String, Integer> temphash = workSheeter.get(type);
            int clickNum = temphash.get(month);
            temphash.replace(month, clickNum + 1);
            workSheeter.replace(type, temphash);
        }
        String respJson = JSON.toJSONString(workSheeter);
        System.out.println(respJson);
        PrintWriter ptr = resp.getWriter();
        ptr.println(respJson);
        ptr.flush();
    }

//    public static void main(String[] args) {
//        try {
//            new TestServlet().doPost(null,null);
//        } catch (ServletException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
