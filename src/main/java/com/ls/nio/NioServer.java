package com.ls.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class NioServer {
  public static void main(String[] args) throws Exception {
    ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
    serverSocketChannel.bind(new InetSocketAddress(8888));
    Selector selector = Selector.open();
    serverSocketChannel.configureBlocking(false);
    serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
    while (true) {
      if (selector.select(2000) == 0) {
        System.out.println("服务器等待了2s");
        continue;
      }
      Set<SelectionKey> selectionKeys = selector.selectedKeys();
      Iterator<SelectionKey> keys = selectionKeys.iterator();
      while (keys.hasNext()) {
        SelectionKey key = keys.next();
        if (key.isAcceptable()) {
          SocketChannel socketChannel = serverSocketChannel.accept();
          System.out.println(socketChannel.getLocalAddress()+":连接成功--"+socketChannel.hashCode());
          socketChannel.configureBlocking(false);
          //socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
          socketChannel.register(selector, SelectionKey.OP_WRITE, ByteBuffer.allocate(1024));
        }
        if (key.isReadable()) {
          SocketChannel channel = (SocketChannel)key.channel();
          ByteBuffer buffer = (ByteBuffer) key.attachment();
          channel.read(buffer);
          System.out.println(new String(buffer.array()));
        }
        //删除当前key，避免重复操作
        keys.remove();
      }
    }
  }
}
