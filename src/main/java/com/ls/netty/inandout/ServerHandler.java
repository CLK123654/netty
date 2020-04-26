package com.ls.netty.inandout;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ServerHandler extends SimpleChannelInboundHandler<Long> {
  @Override
  protected void channelRead0(ChannelHandlerContext channelHandlerContext, Long msg) throws Exception {
    System.out.print("接收数据： ");
    System.out.println(msg);
  }
}
