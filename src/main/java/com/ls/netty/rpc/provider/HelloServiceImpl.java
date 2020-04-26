package com.ls.netty.rpc.provider;

import com.ls.netty.rpc.common.HelloService;

public class HelloServiceImpl implements HelloService {
  @Override
  public String Hello(String msg) {
    return "hello," + msg;
  }
}
