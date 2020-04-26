package com.ls.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;

import java.util.Iterator;
import java.util.List;

public class NettyServerHandler extends ChannelInboundHandlerAdapter {
  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

    //添加到TaskQueue
    ctx.channel().eventLoop().execute(new Runnable() {
      @Override
      public void run() {

      }
    });
    //ctx.channel().eventLoop().execute  添加多个任务时，添加到队列后面去，处于同一个线程
    //ctx.channel().eventLoop().schedule() 添加到ScheduleTaskQueue
    //Channel channel = ctx.channel();
    ByteBuf buf = (ByteBuf) msg;
    System.out.println(buf.toString(CharsetUtil.UTF_8));
    System.out.println("客户端地址："+ctx.channel().remoteAddress());
    SocketChannel socketChannel = (SocketChannel) ctx.channel();
    List<SocketChannel> channels = NettyChannelList.getChannels();
    Iterator<SocketChannel> iterator = channels.iterator();
    while (iterator.hasNext()) {
      if (socketChannel != iterator.next()) {
        socketChannel.writeAndFlush(Unpooled.copiedBuffer(buf));
      }
    }
  }

  @Override
  public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
    ctx.writeAndFlush(Unpooled.copiedBuffer("hello", CharsetUtil.UTF_8));
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    super.exceptionCaught(ctx, cause);
  }
}
