package com.ls.netty.heartbeat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

public class ServerHandler extends ChannelInboundHandlerAdapter {
  @Override
  public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
    if (evt instanceof IdleStateEvent) {
      IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
      switch (idleStateEvent.state()) {
        case ALL_IDLE:
          System.out.println(ctx.channel().remoteAddress()+": 读写空闲");
          break;
        case READER_IDLE:
          System.out.println(ctx.channel().remoteAddress()+": 读空闲");
          break;
        case WRITER_IDLE:
          System.out.println(ctx.channel().remoteAddress()+": 写空闲");
          break;
      }
    }
  }
}
