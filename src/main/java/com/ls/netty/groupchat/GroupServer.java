package com.ls.netty.groupchat;

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


public class GroupServer {

  public static void main(String[] args) {
    NioEventLoopGroup boss = null;
    NioEventLoopGroup worker = null;
    try {
      boss = new NioEventLoopGroup();
      worker = new NioEventLoopGroup();
      ServerBootstrap serverBootstrap = new ServerBootstrap();
      serverBootstrap.group(boss, worker)
              .option(ChannelOption.SO_BACKLOG, 128)
              .channel(NioServerSocketChannel.class)
              .childOption(ChannelOption.SO_KEEPALIVE, true)
              .childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                  ChannelPipeline pipeline = socketChannel.pipeline();
                  //注意这里add的顺序不能颠倒，handle要在后面添加
                  pipeline.addLast("encoder", new StringEncoder());
                  pipeline.addLast("decoder", new StringDecoder());
                  pipeline.addLast(new GroupServerHandler());
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
