package com.ls.netty.protoctcp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ClientHandler extends SimpleChannelInboundHandler<Protocol> {
  @Override
  protected void channelRead0(ChannelHandlerContext channelHandlerContext, Protocol msg) throws Exception {
    System.out.println(msg);
  }

  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    for (int i = 1; i <= 5; i++) {
      String msg = "hello ," + i;
      Protocol protocol = new Protocol();
      protocol.setLen(msg.getBytes().length);
      protocol.setContent(msg.getBytes());
      ctx.writeAndFlush(protocol);
    }
    String over = "over";
    ctx.writeAndFlush(new Protocol(over.getBytes().length, over.getBytes()));
  }
}
