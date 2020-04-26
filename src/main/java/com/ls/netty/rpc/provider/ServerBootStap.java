package com.ls.netty.rpc.provider;

import java.io.Serializable;

public class ServerBootStap {

  public static void main(String[] args) throws InterruptedException {
    Server server = new Server(6666);
    server.startServer0();
  }
}
