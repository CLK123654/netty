package com.ls.netty.groupchat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

public class GroupServerHandler extends SimpleChannelInboundHandler<String> {

  private static ChannelGroup group = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

  /**
   * 连接建立时，第一个执行的方法
   */
  @Override
  public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
    group.add(ctx.channel());
    group.writeAndFlush("[客户端]" + ctx.channel().remoteAddress() + "以连接\n");
    System.out.println("[客户端]" + ctx.channel().remoteAddress() + "上线了");
  }

  @Override
  public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
    group.writeAndFlush("[客户端]" + ctx.channel().remoteAddress() + "断开连接\n");
    System.out.println("[客户端]" + ctx.channel().remoteAddress() + "下线了");
    System.out.println(group.size());
  }

  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    super.channelActive(ctx);
  }

  @Override
  public void channelInactive(ChannelHandlerContext ctx) throws Exception {
    super.channelInactive(ctx);
  }

  @Override
  protected void channelRead0(ChannelHandlerContext ctx, String s) throws Exception {
    Channel channel = ctx.channel();
    group.forEach(ch -> {
      if (ch != channel) {
        ch.writeAndFlush("[客户端]" + ch.remoteAddress() + ": " + s);
      }
    });
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    ctx.close();
  }
}
