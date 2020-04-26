package com.ls.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

public class NioClient {
  public static void main(String[] args) throws Exception {
    SocketChannel client = SocketChannel.open();
    Selector selector = Selector.open();
    InetSocketAddress address = new InetSocketAddress("127.0.0.1", 8888);
    client.configureBlocking(false);
    if ( !client.connect(address)) {
      while ( !client.finishConnect()) {
        System.out.println("尝试连接中。。。。。");
      }
    }
    System.out.println("连接成功！");
    client.write(ByteBuffer.wrap(("this is " + client.toString()).getBytes()));
    //client.read(ByteBuffer.allocate(1024));
    System.in.read();
  }
}
