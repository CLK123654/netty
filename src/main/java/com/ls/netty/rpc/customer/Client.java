package com.ls.netty.rpc.customer;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.lang.reflect.Proxy;

public class Client {

  //private int port;
  //private String host;
  private static ClientHandler clientHandler;

  public  <T> T getClientProxy(Class<T> interfaceCls) throws InterruptedException {
    startClient0();
    return (T) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
            new Class<?>[]{interfaceCls}, clientHandler);
  }

  private static void startClient0() throws InterruptedException {
    clientHandler = new ClientHandler();
    NioEventLoopGroup group = new NioEventLoopGroup();
    Bootstrap bootstrap = new Bootstrap();
    bootstrap.group(group)
            .channel(NioSocketChannel.class)
            .option(ChannelOption.TCP_NODELAY, true)
            .handler(new ChannelInitializer<SocketChannel>() {
      @Override
      protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast(new RpcEncoder());
        pipeline.addLast(clientHandler);
      }
    });
    ChannelFuture sync = bootstrap.connect("127.0.0.1", 6666).sync();
    //sync.channel().closeFuture().sync(); 注意这里这句代码不能添加，如果添加执行startClient0时会阻塞到这里，就不会去执行以上的return (T) Proxy.newProxyInstance
  }
}
