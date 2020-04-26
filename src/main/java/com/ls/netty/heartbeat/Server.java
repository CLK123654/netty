package com.ls.netty.heartbeat;

import com.ls.netty.groupchat.GroupServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
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
                  /**
                   * 第一个参数表示： 当有设置的时间内没发生读事件时，会发送一个心跳检测包检测是否连接
                   * IdleStateHandler被触发后，会将执行程序交给下一个管道去执行
                   *
                   */
                  pipeline.addLast(new IdleStateHandler(3, 5, 7, TimeUnit.SECONDS));
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
