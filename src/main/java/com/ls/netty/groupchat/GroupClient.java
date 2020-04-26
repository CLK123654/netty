package com.ls.netty.groupchat;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.net.InetSocketAddress;
import java.util.Scanner;

public class GroupClient {
  public static void main(String[] args) {
    EventLoopGroup eventExecutors = new NioEventLoopGroup();
    Bootstrap bootstrap = new Bootstrap();
    try {
      bootstrap.group(eventExecutors)
              .channel(NioSocketChannel.class)
              .handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                  ChannelPipeline pipeline = socketChannel.pipeline();
                  pipeline.addLast(new StringDecoder());
                  pipeline.addLast(new StringEncoder());
                  pipeline.addLast(new GroupClientHandler());
                }
              });
      System.out.println("客户端已就绪。。。。。");
      ChannelFuture channelFuture = bootstrap.connect(new InetSocketAddress("127.0.0.1", 6666)).sync();
      if (channelFuture.isSuccess()) {
        System.out.println("已连接至服务端。");
      }
      Channel channel = channelFuture.channel();
      Scanner scanner = new Scanner(System.in);
      while (scanner.hasNext()) {
        String words = scanner.nextLine();
        channel.writeAndFlush(words);
      }
      //channel.closeFuture().sync();
    } catch(Exception e) {
      e.printStackTrace();
    } finally {
      eventExecutors.shutdownGracefully();
    }
  }
}
