package com.servlet;

import com.ServletOpt.LuruOpt;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.UUID;

@WebServlet(name = "InsertDataServlet")
public class InsertDataServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StringBuilder sb = new StringBuilder();
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("*/*");
        response.setHeader("Access-Control-Allow-Origin", "*");
        String contents=request.getReader().readLine();
        String[] cont=contents.split(",");
        UUID name=UUID.randomUUID();
        File file=new File("/home/gly/"+name +".html");
        PrintStream pst=new PrintStream(new FileOutputStream(file));
        System.out.println(cont[0]+cont[1]+cont[2]);
        String imgName = "";

        String imgRes = "<img style=\"width: 100%; min-width:200px; max-width: 800px; margin: 0 auto; display:block;\" src=\"../../../web/resources/picture/"+imgName+"><br>";
        if(cont[0]!=null&&cont[1]!=null){
            //file.mkdir();
            sb.append("<html lang=\"en\">");
            sb.append("<hesd>");
            sb.append("<meta charset=\"UTF-8\">");
            sb.append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1, maximum-scale=1\">");
            sb.append("<title>"+cont[0]+"</title>");
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
            sb.append("<h1 align=\"center\">"+cont[0]+"</h1>");
            sb.append(imgRes);
//            sb.append("<div style=\"width:100%;text-align:center\">");
//            sb.append("<img style=\"width: 100%; min-width:200px; max-width: 800px; margin: 0 auto; display:block;\" src=\"../../../web/resources/picture/login.png\"><br>");
//            sb.append("</div>");
            sb.append("<div class=\"text\" align=\"center\"><h1>相关答案</h1></div> ");
            sb.append("<div align=\"center\">"+cont[1]+"</div>");
            sb.append("<div style=\"padding-top:5%\">");
            sb.append("<h6 id=\"login\" style=\"padding-left: 55%\">未解决？请点击此处 <a href=\"../../../web/html/inputquestion.html\" style=\"color: #1E90FF\">反馈</a></h6>");
            sb.append("</div>");
            sb.append("</body>");
            sb.append("</head>");
            pst.println(sb.toString());
            pst.flush();

            LuruOpt lo=new LuruOpt(request,response);
            lo.LuruMethod(cont[2],cont[0],name,cont[4],cont[3]);

            PrintWriter p = response.getWriter();
            //{"status":"1"}
            p.println("{\"status\":\"1\"}");
            p.flush();
            p.close();
        }
        pst.close();

    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);

    }
}
