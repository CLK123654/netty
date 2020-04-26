package com.ls.netty.protobuf;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

public class NettyClient {
  public static void main(String[] args) {
    EventLoopGroup eventExecutors = new NioEventLoopGroup();
    Bootstrap bootstrap = new Bootstrap();
    try {
      bootstrap.group(eventExecutors)
              .channel(NioSocketChannel.class)
              .handler(new ChannelInitializer<SocketChannel>() {

                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                  socketChannel.pipeline().addLast(new NettyClientHandler());
                }
              });
      System.out.println("客户端已就绪。。。。。");
      ChannelFuture channelFuture = bootstrap.connect(new InetSocketAddress("127.0.0.1", 6666)).sync();
      if (channelFuture.isSuccess()) {
        System.out.println("已连接至服务端。");
      }
      channelFuture.channel().closeFuture().sync();
    } catch(Exception e) {
      e.printStackTrace();
    } finally {
      eventExecutors.shutdownGracefully();
    }
  }
}
