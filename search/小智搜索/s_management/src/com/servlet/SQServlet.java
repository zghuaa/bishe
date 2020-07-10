package com.servlet;

import com.bulk.ElasticSearchOpt;
import com.bulk.IElasticSearchOpt.IElasticSearchOpt;
import com.dao.MyRequest;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

@WebServlet(name = "SQServlet")
public class SQServlet extends HttpServlet {

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
        ElasticSearchOpt elaOpt = new ElasticSearchOpt();
        PrintWriter ptr= resp.getWriter();
        if(requestPara==null){
            elaOpt = new ElasticSearchOpt();
            String fankuiString = elaOpt.searchQuestion();
            ptr.println(fankuiString);
            System.out.println(fankuiString);
            ptr.flush();
            ptr.close();
            return;
        }
        System.out.println(requestPara+"---------------------------");
        HashMap<String,Object> paraMap = new HashMap<>();
        String[] params = requestPara.split("&");
        for (String para: params) {
            String repKey = para.split("=")[0];
            String repValue = para.split("=")[1];
            paraMap.put(repKey,repValue);
        }
        System.out.println(paraMap+"********************");
        MyRequest myRequest = new MyRequest(paraMap);
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
        }catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        ptr.println(responJson);
        System.out.println(responJson+"+++++++++++++++++++++++++++++++++");
        ptr.flush();
        ptr.close();
    }
}
