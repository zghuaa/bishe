package com.handler;


import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

import static io.netty.handler.codec.http.HttpHeaderNames.*;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;


//实现对网络数据的读写
public class ServerHandler extends ChannelInboundHandlerAdapter { //回调类
    private static Logger logger = Logger.getLogger(ServerHandler.class);
    private Charset fileCharset = Charset.forName("utf-8");
    private CharsetDecoder fileDecoder = fileCharset.newDecoder();
    private HttpResponse response;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //读数据时会调用该方法
        FullHttpRequest fullHR = (FullHttpRequest) msg;
        String url = fullHR.uri();
//        System.out.println(url);
        if (!url.equalsIgnoreCase("/favicon.ico")) {
            if (url.equals("/")) {
                url = "/web/html/login.html";
            }
            if (url.contains("web")) {
                String resourcePath = "/home/wq/robot_service_1" + url;
                logger.info("resourcePath    " + resourcePath);
                FileInputStream fin = new FileInputStream(resourcePath);
                byte[] imgByte;
                if (resourcePath.contains(".html") || resourcePath.contains(".css") || resourcePath.contains(".js")) {
                    FileChannel fileRead = fin.getChannel();
                    MappedByteBuffer fileReadBuffer = fileRead.map(FileChannel.MapMode.READ_ONLY, 0, fin.available());
                    CharBuffer fileCharBuffer = fileDecoder.decode(fileReadBuffer);
                    String file = fileCharBuffer.toString();
                    response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(file.getBytes("utf-8")));
                    if (resourcePath.contains(".js")) {
                        response.headers().set(CONTENT_TYPE, "application/javascript; charset=utf-8");
                    } else if (resourcePath.contains(".css")) {
                        response.headers().set(CONTENT_TYPE, "text/css; charset=utf-8");
                    } else {
                        response.headers().set(CONTENT_TYPE, "text/html; charset=utf-8");
                    }
                } else {
                    if (url.contains(".png") || url.contains(".jpg")) {
                        imgByte = new byte[fin.available()];
                        int flag = fin.read(imgByte);
                        if (flag < 0) {
                            imgByte = "无法加载该图片".getBytes("utf-8");
                        }
                        response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(imgByte));
                        response.headers().set(CONTENT_TYPE, "*/*");
                    }
                }
                response.headers().setInt(CONTENT_LENGTH, ((DefaultFullHttpResponse) response).content().readableBytes());
                response.headers().set("Access-Control-Allow-Origin", "*");
                ctx.writeAndFlush(response);
                fin.close();
                fullHR.release();
            } else {
                ctx.fireChannelRead(msg);
            }
        }
    }
}





