package com.ls.netty.websocket;

import com.ls.netty.heartbeat.ServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;


public class Server {

  public static void main(String[] args) {
    NioEventLoopGroup boss = null;
    NioEventLoopGroup worker = null;
    try {
      boss = new NioEventLoopGroup();
      worker = new NioEventLoopGroup();
      ServerBootstrap serverBootstrap = new ServerBootstrap();
      serverBootstrap.group(boss, worker)
              .channel(NioServerSocketChannel.class)
              .handler(new LoggingHandler(LogLevel.INFO))
              .childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                  ChannelPipeline pipeline = socketChannel.pipeline();
                  //因为基于http协议，使用http的编码解码器
                  pipeline.addLast(new HttpServerCodec());
                  //是以块方式写
                  pipeline.addLast(new ChunkedWriteHandler());
                  //http数据在传输过程中是分段的，HttpObjectAggregator是可以将多个段聚合起来
                  //这也是请求的数据块比较大时，就会发生多次http请求
                  pipeline.addLast(new HttpObjectAggregator(8192));
                  pipeline.addLast(new WebSocketServerProtocolHandler("/"));
                  pipeline.addLast(new ServerHandler());
                }
              });
      System.out.println("服务器已启动。。。");
      ChannelFuture future = serverBootstrap.bind(6666).sync();
      future.channel().closeFuture().sync();
    }catch (Exception e){

    }finally {
      worker.shutdownGracefully();
      boss.shutdownGracefully();
    }
  }
}
