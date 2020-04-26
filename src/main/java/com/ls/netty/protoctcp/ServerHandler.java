package com.ls.netty.protoctcp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ServerHandler extends SimpleChannelInboundHandler<Protocol> {
  private int count = 0;
  private static String OVER = "over";

  @Override
  protected void channelRead0(ChannelHandlerContext channelHandlerContext, Protocol msg) throws Exception {
    String content = new String(msg.getContent());
    if ( !content.equals(OVER)) {
      System.out.println("接收数据： " + (++count));
      System.out.println("len:" + msg.getLen());
      System.out.println("content:" + content);
    } else {
      System.out.println("----------------------------------");
    }
  }
}
