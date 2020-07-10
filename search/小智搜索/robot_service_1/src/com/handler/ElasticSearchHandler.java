package com.handler;


import com.Db.dao.Question;
import com.MyHttpDecoder.MyHttpPostDecoder;
import com.bulk.IElasticSearchOpt.IElasticSearchOpt;
import com.Db.dao.Information;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpResponse;
import org.apache.log4j.Logger;

import java.lang.reflect.Method;
import java.util.HashMap;

import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_LENGTH;
import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

public class ElasticSearchHandler extends ChannelInboundHandlerAdapter {
    private static Logger logger = Logger.getLogger(ElasticSearchHandler.class);
    private String url = null;
    private MyHttpPostDecoder myHttpPostDecoder = null;
    private String jsonList = null;
    private Information information;
    private Question question;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        FullHttpRequest request = (FullHttpRequest) msg;
        url = request.uri();
        if (!url.equals("/favicon.ico")) {
            //这部分url都有search
            myHttpPostDecoder = new MyHttpPostDecoder(request);
            HashMap<String, Object> myhash = myHttpPostDecoder.httpPostDecode();
            if (request.method().equals(HttpMethod.POST)) {
                logger.info("url:  "+url+"\t para:  "+myhash);
                String methodName = url.split("/")[1];
                Class elsClass = Class.forName("com.bulk.ElasticSearchOpt");
                logger.info("methodName: "+methodName);
                Method elaMethod;
                IElasticSearchOpt iElaOpt = (IElasticSearchOpt) elsClass.newInstance();
                if(methodName.equals("addQuestion")){
                    question = new Question(myhash);
                    elaMethod = elsClass.getMethod(methodName,Question.class);
                    jsonList = (String) elaMethod.invoke(iElaOpt, information);
                }else {
                    information = new Information(myhash);
                    elaMethod = elsClass.getMethod(methodName, Information.class);
                    jsonList = (String) elaMethod.invoke(iElaOpt, information);
                }
                request.release();
                logger.info("responseJson:  "+jsonList);
                HttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(jsonList.getBytes("utf-8")));
                response.headers().set(CONTENT_TYPE, "text/html; charset=utf-8");
                response.headers().set("Access-Control-Allow-Origin", "*");
                response.headers().setInt(CONTENT_LENGTH, ((DefaultFullHttpResponse) response).content().readableBytes());
                ctx.writeAndFlush(response);
            }
        }
    }
}

