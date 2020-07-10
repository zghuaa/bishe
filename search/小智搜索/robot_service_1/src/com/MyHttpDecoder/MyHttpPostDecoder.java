package com.MyHttpDecoder;

import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.multipart.Attribute;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;
import io.netty.handler.codec.http.multipart.InterfaceHttpPostRequestDecoder;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class MyHttpPostDecoder {
    private HttpPostRequestDecoder post_decoder = null;
    private List<InterfaceHttpData> parmList = null;
    private static HashMap<String,Object> parmMap;
    private FullHttpRequest request;
    private InterfaceHttpPostRequestDecoder requestoffer;
    private static final Logger logger = Logger.getLogger(MyHttpPostDecoder.class);

    public MyHttpPostDecoder(FullHttpRequest request1) {
        this.request = request1;
        logger.info("开始处理Post请求...");
    }

    public HashMap<String,Object> httpPostDecode() {
        parmMap = new HashMap<>();
        post_decoder = new HttpPostRequestDecoder(request);
        requestoffer = post_decoder.offer(request);
        parmList = requestoffer.getBodyHttpDatas();
        for (InterfaceHttpData parm : parmList) {
            Attribute data = (Attribute) parm;
            try {
                parmMap.put(data.getName(), data.getValue());
            } catch (IOException e) {
                logger.error("Method: httpPostDecode",e);
                parmMap.put(data.getName(), "");

            }
        }
        logger.info("接收到的post请求的k,v是" + parmMap);
        return parmMap;
    }
}
