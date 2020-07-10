package com.MyHttpDecoder;

import org.apache.log4j.Logger;

import java.util.HashMap;

public class MyHttpGetDecoder {
    private String request_url;
    private String path;
    private String request_path = "path";
    private HashMap<String, Object> requestMap;
    private static final Logger logger = Logger.getLogger(MyHttpGetDecoder.class);

    public MyHttpGetDecoder(String url) {
        this.request_url = url;
        requestMap = new HashMap<>();
        logger.info("开始处理Get请求");
    }

    public HashMap decoderUrl() {
        path = request_url.split("\\?")[0];
        String request = request_url.split("\\?")[1];
        requestMap.put(request_path, path);
        String[] requestParas = request.split("&");
        for (String para : requestParas) {
            requestMap.put(para.split("=")[0], para.split("=")[1]);
        }
        logger.info("解出的get请求的k,v值: " + requestMap);
        return requestMap;
    }
}
