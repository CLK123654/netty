package com.ls.netty.rpc.customer;

import com.alibaba.fastjson.JSONObject;
import com.ls.netty.rpc.common.RpcProtoc;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class RpcEncoder extends MessageToByteEncoder<RpcProtoc> {
  @Override
  protected void encode(ChannelHandlerContext channelHandlerContext, RpcProtoc o, ByteBuf byteBuf) throws Exception {
    //将java对象转成json字符串
    String s = JSONObject.toJSONString(o);
    byte[] content = s.getBytes();
    byteBuf.writeInt(content.length);
    byteBuf.writeBytes(content);
  }
}
