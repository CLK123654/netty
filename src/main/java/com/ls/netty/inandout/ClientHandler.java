package com.ls.netty.inandout;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ClientHandler extends SimpleChannelInboundHandler<Long> {
  @Override
  protected void channelRead0(ChannelHandlerContext channelHandlerContext, Long msg) throws Exception {
    System.out.println(msg);
  }

  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    System.out.println("客户端发送数据");
    //这里如果发送的数据类型和编码器规定的类型不匹配,那么编码器的encode方法不会被执行
    ctx.writeAndFlush(123456L);
  }
}
