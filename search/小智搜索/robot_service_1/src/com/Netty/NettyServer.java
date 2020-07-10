package com.Netty;


import com.handler.ElasticSearchHandler;
import com.handler.OptUserHandler;
import com.handler.ServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.apache.log4j.Logger;

import java.util.concurrent.Executors;

public class NettyServer {
    private static Logger logger = Logger.getLogger(NettyServer.class);
    public void start(String IP, int Port) {
        logger.info("IP: "+IP+"\tPORT: "+Port);
        EventLoopGroup bossGroup = new NioEventLoopGroup(2, Executors.newCachedThreadPool());//接收组，处理来访问服务器的客户的连接请求
        EventLoopGroup workGroup = new NioEventLoopGroup(4, Executors.newCachedThreadPool());//工作组，实现数据的读写
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();//服务端来设置通道参数的工具
            bootstrap.group(bossGroup, workGroup)//将两个工作线程与通道绑定
                    .channel(NioServerSocketChannel.class)//指定NIO模式
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {//设定回调类
                            socketChannel.pipeline().addLast(new HttpResponseEncoder());//server端发送的是httpResponse,要进行编码

                            socketChannel.pipeline().addLast(new HttpRequestDecoder());//server端接收的是httpRequest,要进行解码

                            socketChannel.pipeline().addLast(new HttpObjectAggregator(65535));
                            //等待解码后的报文头和报文体一起扔给下一层
                            socketChannel.pipeline().addLast(new ChunkedWriteHandler());

                            socketChannel.pipeline().addLast("网页一些资源的返回",new ServerHandler());//返回首页

                            socketChannel.pipeline().addLast("处理登录注册",new OptUserHandler());

                            socketChannel.pipeline().addLast("el的一些处理",new ElasticSearchHandler());

                        }
                    })//设置回调
                    .option(ChannelOption.SO_BACKLOG, 128)//设置TCP缓冲区
                    .childOption(ChannelOption.TCP_NODELAY,true)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);//设置长连接
            ChannelFuture future = bootstrap.bind(IP, Port).sync();//绑定端口
            //阻止程序关闭
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        NettyServer nettyServer = new NettyServer();
        nettyServer.start("0.0.0.0", 9999);
    }
}

