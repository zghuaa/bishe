package com.handler;

import com.Db.dao.UserDao;
import com.Jedis.OptUserJedis;
import com.MyHttpDecoder.MyHttpGetDecoder;
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

public class OptUserHandler extends ChannelInboundHandlerAdapter {
    private String response_json;
    private static final Logger logger = Logger.getLogger(OptUserHandler.class);
    private HttpResponse response;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //此handler只处理登录与注册业务,若不是则flush
        FullHttpRequest request = (FullHttpRequest) msg;
        String request_url = request.uri();
        if (!request_url.equals("/favicon.ico")) {
            if (request.method() == HttpMethod.POST) {
                if (request_url.contains("User")) {
                    Class jedisClass = Class.forName("com.Jedis.OptUserJedis");
                    OptUserJedis userJedis = (OptUserJedis) jedisClass.newInstance();
                    String methodName = request_url.split("/")[1];
                    logger.info("Method: " + methodName);
                    Method jedisMethod = jedisClass.getMethod(methodName, FullHttpRequest.class);
                    response_json = (String) jedisMethod.invoke(userJedis, request);
                    logger.info("对于User的操作完成\t" + response_json);
                    response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(response_json.getBytes("utf-8")));
                    response.headers().set(CONTENT_TYPE, "*/*");
                    response.headers().set("Access-Control-Allow-Origin", "*");
                    response.headers().setInt(CONTENT_LENGTH, ((DefaultFullHttpResponse) response).content().readableBytes());
                    ctx.writeAndFlush(response);
                } else {
                    //进入下一个handler
                    ctx.fireChannelRead(msg);
                }
            } else if (request_url.contains("flag")) {
                //说明是从邮箱发过来的get请求,再判断email在不在redis中,若有则落库
                logger.info("判断为第二次进行验证,url: " + request_url);
                System.out.println(request_url);
                MyHttpGetDecoder myHttpGetDecoder = new MyHttpGetDecoder(request_url);
                HashMap<String, Object> userMap = myHttpGetDecoder.decoderUrl();
                logger.info("接收到的参数: "+userMap);
                UserDao userDao = new UserDao(userMap);
                OptUserJedis optUserJedis = new OptUserJedis();
                boolean is_complite = optUserJedis.checkUser(userDao.getEmail());
                if (is_complite) {
                    response_json = "<script>\n" +
                            "    alert(\"验证成功，请重新登录\");\n"
                            + "window.close();" +
                            "</script>";
                } else {
                    response_json = "<script>\n" +
                            "    alert(\"验证失败，请重新注册\");\n"
                            + "window.close();" +
                            "</script>";
                }
                request.release();
                response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(response_json.getBytes("utf-8")));
                logger.info("第二次验证完成");
                response.headers().set(CONTENT_TYPE, "*/*;charset=utf-8");
                response.headers().set("Access-Control-Allow-Origin", "*");
                response.headers().setInt(CONTENT_LENGTH, ((DefaultFullHttpResponse) response).content().readableBytes());
                ctx.writeAndFlush(response);
            }
        }
    }

//    public static void main(String[] args) {
//        MyHttpGetDecoder myHttpGetDecoder = new MyHttpGetDecoder("http://localhost:9999/?email=1111&flag=register");
//        HashMap<String, String> userMap = myHttpGetDecoder.decoderUrl();
//        System.out.println(userMap);
//        UserDao userDao = new UserDao(userMap);
//        System.out.println(userDao);
//    }
}
