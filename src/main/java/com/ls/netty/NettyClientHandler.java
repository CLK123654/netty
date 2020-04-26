package com.ls.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.util.Scanner;

public class NettyClientHandler extends ChannelInboundHandlerAdapter {
  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
//    ctx.channel().eventLoop().execute(new Runnable() {
//      @Override
//      public void run() {
//       while (true) {
//        Scanner scanner = new Scanner(System.in);
//        String words = scanner.nextLine();
//        ctx.writeAndFlush(Unpooled.copiedBuffer(words, CharsetUtil.UTF_8));
//        // ctx.channel().read();
//       }
//      }
//    });
    ctx.writeAndFlush(Unpooled.copiedBuffer("hello", CharsetUtil.UTF_8));
  }

  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    ByteBuf buf = (ByteBuf)msg;
    System.out.println(buf.toString(CharsetUtil.UTF_8));
  }
}
