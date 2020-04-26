package com.ls.netty.groupchat;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

public class GroupClientHandler extends SimpleChannelInboundHandler<String> {
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
    //ctx.writeAndFlush(Unpooled.copiedBuffer("hello", CharsetUtil.UTF_8));
  }

  @Override
  protected void channelRead0(ChannelHandlerContext ctx, String s) throws Exception {
    System.out.println(s.trim());
  }
}
