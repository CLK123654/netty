package com.ls.nio.chatroom;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;


/**
 * @author huanghb
 */
public class Server {

  private static ServerSocketChannel server;
  private static Selector selector;

  public static int PORT = 6666;

  /**
   * 选择器selector超时时间 单位毫秒
   */
  public static int TIME_OUT = 2000;

  /**
   * 缓冲区大小  单位字节
   */
  public static int BUFF_SIZE = 1024;

  public static void main(String[] args) throws IOException {
    server = ServerSocketChannel.open();
    server.bind(new InetSocketAddress(PORT));
    server.configureBlocking(false);
    selector = Selector.open();
    server.register(selector, SelectionKey.OP_ACCEPT);
    while (true) {
      while (selector.select(TIME_OUT) == 0) {
        System.out.println("等待事件。。。。。");
      }
      Iterator<SelectionKey> iterator = selector.keys().iterator();
      while (iterator.hasNext()) {
        SelectionKey key = iterator.next();
        if (key.isAcceptable()) {
          SocketChannel client = server.accept();
          System.out.println("客户端"+client.getRemoteAddress() + "-" + client.hashCode() + " 以连接");
          client.configureBlocking(false);
          client.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(BUFF_SIZE));
        }
        if (key.isReadable()) {
          read(key);
        }
        iterator.remove();
      }
    }
  }

  public static void read(SelectionKey key) {
    SocketChannel client = (SocketChannel)key.channel();
    ByteBuffer buffer = (ByteBuffer)key.attachment();
    try {
      client.read(buffer);
      sendToOthers(buffer, client);
    } catch (IOException e) {
      try {
        key.channel();
        client.close();
      } catch (IOException ex) {
        ex.printStackTrace();
      }
    }
  }

  public static void sendToOthers(ByteBuffer buffer, SocketChannel self) throws IOException {
    for(SelectionKey key : selector.keys()) {
      SocketChannel client = (SocketChannel)key.channel();
      if (client instanceof SocketChannel && client != self) {
        client.write(buffer);
      }
    }
  }
}
