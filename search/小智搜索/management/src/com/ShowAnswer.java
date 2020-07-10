package com;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "ShowAnswer")
public class ShowAnswer extends HttpServlet {
    private static String question;   //文本框中的问题
    private static String answer;     //文本框中的答案
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        PrintWriter out= response.getWriter();     //获取输出对象
        out.println("<html lang=\"en\">");
        out.println("<hesd>");
        out.println("<meta charset=\"UTF-8\">");
        out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1, maximum-scale=1\">");
        out.println("<title>"+question+"</title>");
        //out.println("<title>注册以后</title>");
        out.println("<script src=\"../../../web/js/jquery-3.3.1.js\" type=\"text/javascript\"></script>");
        out.println("<script src=\"../../../web/js/jquery-3.3.1.min.js\" type=\"text/javascript\"></script>");
        out.println("<script src=\"../../../web/js/updateTile.js\"></script>");
        out.println("<link rel=\"stylesheet\" href=\"../../../web/css/cannotcopy.css\">");
        out.println("<style>\n" +
                "        .text{\n" +
                "            word-wrap: break-word;\n" +
                "            word-break: normal;\n" +
                "            padding-top: 15px\n" +
                "        }\n" +
                "        img{\n" +
                "            padding-top: 15px;\n" +
                "        }\n" +
                "    </style>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1 align=\"center\">"+question+"</h1>");
        //out.println("<h1 align=\"center\">注册以后</h1>");
        out.println("<div style=\"width:100%;text-align:center\">");
        out.println("<img style=\"width: 100%; min-width:200px; max-width: 800px; margin: 0 auto; display:block;\" src=\"../../../web/resources/picture/login.png\"><br>");
        out.println("</div>");
        out.println("<div class=\"text\" align=\"center\"><h1>相关答案</h1></div> ");
        out.println("<div align=\"center\">"+answer+"</div>");
        //out.println("<div align=\"center\">需先注册账号</div>");
        out.println("<div style=\"padding-top:5%\">");
        out.println("<h6 id=\"login\" style=\"padding-left: 55%\">未解决？请点击此处 <a href=\"../../../web/html/inputquestion.html\" style=\"color: #1E90FF\">反馈</a></h6>");
        out.println("</div>");
        out.println("</body>");
        out.println("</head>");
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);

    }

    public static void main(String[] args) {
        int i = 0;
        System.out.println(i++);
        int j = 0;
        System.out.println(++j);
    }
}
