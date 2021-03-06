package com.ls.netty.protoctcp;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class Server {
  public static void main(String[] args) throws InterruptedException {
    NioEventLoopGroup boss = new NioEventLoopGroup();
    NioEventLoopGroup worker = new NioEventLoopGroup();

    ServerBootstrap bootstrap = new ServerBootstrap();
    bootstrap.group(boss, worker).channel(NioServerSocketChannel.class).childHandler(new ServerInitializer());
    ChannelFuture sync = bootstrap.bind(6666).sync();
    System.out.println("服务器准备就绪");
    sync.channel().closeFuture().sync();
  }
}
