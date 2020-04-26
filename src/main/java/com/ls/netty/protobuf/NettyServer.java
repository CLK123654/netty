package com.ls.netty.protobuf;

import com.ls.netty.NettyChannelList;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;

public class NettyServer {
  public static void main(String[] args) {
    //创建两个线程组，两个都是无限循环
    EventLoopGroup bossGroup = new NioEventLoopGroup();
    EventLoopGroup workerGroup = new NioEventLoopGroup();

    //创建服务器端的启动对象，配置参数
    ServerBootstrap bootstrap = new ServerBootstrap();
    try {
      bootstrap.group(bossGroup, workerGroup)
              .channel(NioServerSocketChannel.class)
              .option(ChannelOption.SO_BACKLOG, 128)//设置线程队列数
              .childOption(ChannelOption.SO_KEEPALIVE, true)
             // .option(ChannelOption.TCP_NODELAY, true)
              .childHandler(new ChannelInitializer<SocketChannel>() {

                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                  ChannelPipeline pipeline = socketChannel.pipeline();
                  //pipeline.addLast(new ProtobufDecoder(""));
                  pipeline.addLast(new NettyServerHandler());
                  System.out.println("一个客户端已连接");
                }
              });
      System.out.println("服务器已就绪。。。。");
      ChannelFuture cf = bootstrap.bind(6666).sync();
      //对关闭通道进行监听
      cf.channel().closeFuture().sync();
    }catch (Exception e) {
      e.printStackTrace();
    } finally {
      workerGroup.shutdownGracefully();
      bossGroup.shutdownGracefully();
    }
  }
}
