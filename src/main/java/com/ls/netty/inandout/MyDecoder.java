package com.ls.netty.inandout;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class MyDecoder extends ByteToMessageDecoder {

  /**
   * 根据输入数据的大小 以及方法内的处理 该方法可能被调用多次
   * 比如要解码的数据大小与16字节，而该方法中一次只处理了8个字节，故会自动调用两次该方法
   * 且该方法调用之后，紧接着也会调用一次该MyDecoder处理器的下一个handler（这里离的下一个是指pipeline中添加的顺序）
   * 即  MyDecoder->handler->MyDecoder->handler
   *
   * @param channelHandlerContext
   * @param in
   * @param out
   * @throws Exception
   */
  @Override
  protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf in, List<Object> out) throws Exception {
    //解析成Long
    if (in.readableBytes() >= 8) {
      out.add(in.readLong());
    }
  }
}
