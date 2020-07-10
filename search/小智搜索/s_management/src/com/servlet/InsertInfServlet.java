package com.servlet;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bulk.ElasticSearchOpt;
import com.bulk.IElasticSearchOpt.IElasticSearchOpt;
import com.dao.MyRequest;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;


@WebServlet(name = "InsertInfServlet")
public class InsertInfServlet extends HttpServlet {

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
        MyRequest myRequest = new MyRequest(jsonObject.getInnerMap());
        System.out.println(myRequest.toString());
        Class elaInf = null;
        String responJson = null;
        String fileName = UUID.randomUUID() +".html";
        File file=new File(fileName);
        myRequest.setContent("/web/resources/"+fileName);
        PrintStream pst=new PrintStream(new FileOutputStream("/home/gly/"+ fileName));
        StringBuilder sb = new StringBuilder();

        sb.append("<html lang=\"en\">");
        sb.append("<hesd>");
        sb.append("<meta charset=\"UTF-8\">");
        sb.append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1, maximum-scale=1\">");
        sb.append("<title>"+myRequest.getTitle()+"</title>");
        sb.append("<script src=\"../../../web/js/jquery-3.3.1.js\" type=\"text/javascript\"></script>");
        sb.append("<script src=\"../../../web/js/jquery-3.3.1.min.js\" type=\"text/javascript\"></script>");
        sb.append("<script src=\"../../../web/js/updateTile.js\"></script>");
        sb.append("<link rel=\"stylesheet\" href=\"../../../web/css/cannotcopy.css\">");
        sb.append("<style>\n" +
                "        .text{\n" +
                "            word-wrap: break-word;\n" +
                "            word-break: normal;\n" +
                "            padding-top: 15px\n" +
                "        }\n" +
                "        img{\n" +
                "            padding-top: 15px;\n" +
                "        }\n" +
                "    </style>");
        sb.append("</head>");
        sb.append("<body>");
        sb.append("<h1 align=\"center\">"+myRequest.getTitle()+"</h1>");
//        sb.append(imgRes);
//            sb.append("<div style=\"width:100%;text-align:center\">");
//            sb.append("<img style=\"width: 100%; min-width:200px; max-width: 800px; margin: 0 auto; display:block;\" src=\"../../../web/resources/picture/login.png\"><br>");
//            sb.append("</div>");
        sb.append("<div class=\"text\" align=\"center\"><h1>相关答案</h1></div> ");
        sb.append("<div align=\"center\">"+myRequest.getAnswer()+"</div>");
        sb.append("<div style=\"padding-top:5%\">");
        sb.append("<h6 id=\"login\" style=\"padding-left: 55%\">未解决？请点击此处 <a href=\"../../../web/html/inputquestion.html\" style=\"color: #1E90FF\">反馈</a></h6>");
        sb.append("</div>");
        sb.append("</body>");
        sb.append("</head>");
        pst.println(sb.toString());
        pst.flush();
        pst.close();


        try {
            elaInf = Class.forName("com.bulk.ElasticSearchOpt");
            IElasticSearchOpt iElaOpt = (IElasticSearchOpt) elaInf.newInstance();
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
