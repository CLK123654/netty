package com.ls.netty.rpc.provider;

import com.alibaba.fastjson.JSONObject;
import com.ls.netty.rpc.common.RpcProtoc;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

//使用fastjson将获取的数据进行解码
public class RpcDecoder extends ByteToMessageDecoder {
  @Override
  protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
    int len = byteBuf.readInt();
    byte[] contents = new byte[len];
    byteBuf.readBytes(contents);
    //这里获取的字符串  由于编码的时候已经转成了json格式的字符串，所以这里得到的也是json字符串
    String str = new String(contents);
    //json字符串转java对象
    list.add(JSONObject.parseObject(str, RpcProtoc.class));
  }
}
