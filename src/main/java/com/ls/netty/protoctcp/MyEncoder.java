package com.ls.netty.protoctcp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 无论编码还是解码  类型不一致时，该编码解码的handler不会执行
 */
public class MyEncoder extends MessageToByteEncoder<Protocol> {

  @Override
  protected void encode(ChannelHandlerContext channelHandlerContext, Protocol msg, ByteBuf byteBuf) throws Exception {
    int len = msg.getLen();
    byteBuf.writeInt(len);
    byteBuf.writeBytes(msg.getContent());
  }
}
