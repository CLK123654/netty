package com.ls.netty.inandout;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.concurrent.EventExecutorGroup;

import java.nio.ByteBuffer;

/**
 * 无论编码还是解码  类型不一致时，该编码解码的handler不会执行
 */
public class MyEncoder extends MessageToByteEncoder<Long> {

  @Override
  protected void encode(ChannelHandlerContext channelHandlerContext, Long msg, ByteBuf byteBuf) throws Exception {
    byteBuf.writeLong(msg);
  }
}
