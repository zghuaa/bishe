//package com;
//
//import java.io.FileOutputStream;
//import java.io.PrintStream;
//import java.util.Properties;
//
//public class test {
//    public void tt(){
//        StringBuilder sb = new StringBuilder();
//        Properties fileProperties = getProperties("file");
//        Properties sqlProperties = getProperties("sql");
//
//
//
//        PrintStream printStream = new PrintStream(new FileOutputStream(
//                "report.html"));
//        sb.append("<html>");
//        sb.append("<head>");
//        sb.append("<title>每日运营报表</title>");
//        sb.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />");
//        sb.append("<style type=\"text/css\">");
//        sb.append("TABLE{border-collapse:collapse;border-left:solid 1 #000000; border-top:solid 1 #000000;padding:5px;}");
//        sb.append("TH{border-right:solid 1 #000000;border-bottom:solid 1 #000000;}");
//        sb.append("TD{font:normal;border-right:solid 1 #000000;border-bottom:solid 1 #000000;}");
//        sb.append("</style></head>");
//        sb.append("<body bgcolor=\"#FFF8DC\">");
//        sb.append("<div align=\"center\">");
//        sb.append("<br/>");
//        sb.append("<br/>");
//        List<Map<String, Object>> result1 = getRpt(sqlProperties
//                .getProperty("sql1"));
//        for (Map.Entry<String, Object> m : result1.get(0).entrySet()) {
//            sb.append(fileProperties.getProperty("file1"));
//            sb.append(m.getValue());
//        }
//        sb.append("<br/><br/>");
//        输出
//        sb.append("</div></body></html>");
//        printStream.println(sb.toString());
//    }
//
//
//}
