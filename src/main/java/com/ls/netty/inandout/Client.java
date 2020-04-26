package com.ls.netty.inandout;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class Client {
  public static void main(String[] args) throws InterruptedException {
    NioEventLoopGroup boss = new NioEventLoopGroup();

    Bootstrap bootstrap = new Bootstrap();
    bootstrap.group(boss).channel(NioSocketChannel.class).handler(new ClientInitializer());
    ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 6666).sync();
    channelFuture.channel().closeFuture().sync();
  }
}
