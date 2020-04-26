package com.ls.netty.rpc.customer;

import com.ls.netty.rpc.common.RpcProtoc;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class ClientHandler extends ChannelInboundHandlerAdapter implements InvocationHandler {

  private ChannelHandlerContext ctx;
  private String ret;
  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    this.ctx = ctx;
  }

  @Override
  public synchronized void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    ByteBuf byteBuf = (ByteBuf) msg;
    ret = byteBuf.toString(CharsetUtil.UTF_8);
    notify();
  }

  @Override
  public synchronized Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    RpcProtoc rpcProtoc = new RpcProtoc();
    rpcProtoc.setMethodName(method.getName());
    rpcProtoc.setParams(args);
    ctx.writeAndFlush(rpcProtoc);
    wait();
    return ret;
  }
}
