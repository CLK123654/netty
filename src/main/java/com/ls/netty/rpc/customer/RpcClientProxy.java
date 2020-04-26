package com.ls.netty.rpc.customer;

import java.lang.reflect.Proxy;

public class RpcClientProxy {
  public static <T> T clientProxy(Class<T> interfaceCls) {
    return (T) Proxy.newProxyInstance(interfaceCls.getClassLoader(),
            new Class<?>[]{interfaceCls}, new ClientHandler());
  }
}
