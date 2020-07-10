package com.staff.register;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;

public class CsvToJson {
    public static String getJsonFromURL(URL url,String separator){
        String csv= null;
        try {
            csv = IOUtils.toString(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return getJson(csv,separator);
    }

    public static String getJson(String content,String separator){
        StringBuilder sb=new StringBuilder("{\"code\":0,\"msg\":\"\",\"count\":500,\"data\":[\n");
        String csv=content;
        String csvValues[]=csv.split("\n");
        String header[]=csvValues[0].split(separator);
        for (int i=1;i<csvValues.length;i++){
            sb.append("\t")
              .append("{")
              .append("\n");
            String tmp[]=csvValues[i].split(separator);
            for (int j=0;j<tmp.length;j++){
                sb.append("\t")
                   .append("\t\"")
                   .append(header[j].replaceAll("\\s*","").replace("\"",""))
                   .append("\":\"")
                   .append(tmp[j].replaceAll("\\s*","").replace("\"",""))
                   .append("\"");
                if (j<tmp.length-1){
                    sb.append(",\n");
                }else {
                    sb.append("\n");
                }
            }
            if(i<csvValues.length-1){
                sb.append("\t},\n");
            }else {
                sb.append("\t}\n");
            }
        }
        sb.append("]}");
        return sb.toString();
    }

    public static String getJsonFromFile(String fileName,String separater){
        byte[] bytes=null;
        try {
            bytes= FileUtils.readFileToByteArray(new File(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String csv= null;
        try {
            csv = new String(bytes,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return getJson(csv,separater).replace("\r","").replace("\n","").replace("\b","").replaceAll("\\s*","");
    }

    public static void main(String[] args) {
        System.out.println(getJsonFromFile("/home/gly/AAA/pwd.csv",","));
    }
}
