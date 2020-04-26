package com.ls.netty.rpc.provider;

import com.ls.netty.rpc.common.HelloService;
import com.ls.netty.rpc.common.RpcProtoc;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.lang.reflect.Method;

public class ServerHandler extends SimpleChannelInboundHandler<RpcProtoc> {
  @Override
  protected void channelRead0(ChannelHandlerContext ctx, RpcProtoc rpcProtoc) throws Exception {
    HelloService service = new HelloServiceImpl();
    Object[] parms = rpcProtoc.getParams();
    Class<?>[] type = new Class[parms.length];
    for (int i = 0; i < parms.length; i++) {
      type[i] = parms[i].getClass();
    }
    Method method = service.getClass().getMethod(rpcProtoc.getMethodName(), type);
    String ret = (String) method.invoke(service, parms);
    System.out.println("服务器返回的结果是" + ret);
    ctx.writeAndFlush(Unpooled.copiedBuffer(ret, CharsetUtil.UTF_8));
  }
}
