package com.ls.nio.chatroom;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;

/**
 * @author huanghb
 */
public class Client {
  private static Selector selector;
  private static SocketChannel socketChannel;
  public static String IP = "127.0.0.1";
  public static int PORT = 6666;
  public static int TIME_OUT = 2000;
  public static int BUFF_SIZE = 1024;

  public void init() {
    try {
      socketChannel = SocketChannel.open();
      if (!socketChannel.connect(new InetSocketAddress(IP, PORT))) {
        System.out.println("正在连接服务器。。。。");
        while(!socketChannel.finishConnect()) {
        }
      }
      System.out.println("服务器连接成功！");
      selector = Selector.open();
      socketChannel.configureBlocking(false);
      socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(BUFF_SIZE));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void read() {
    try {
      selector.select();
      Iterator<SelectionKey> keys = selector.keys().iterator();
      while (keys.hasNext()) {
        SelectionKey key = keys.next();
        if (key.isReadable()) {
          ByteBuffer buffer = (ByteBuffer) key.attachment();
          socketChannel.read(buffer);
          System.out.println(buffer.array());
        }
        keys.remove();
      }
    }catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void sendInfo() {
    Scanner scanner = new Scanner(System.in);
    String line = scanner.nextLine();
    try {
      socketChannel.write(ByteBuffer.wrap(line.getBytes()));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    Client client = new Client();
    client.init();
    new Thread() {
      @Override
      public void run() {
        while (true) {
          client.read();
        }
      }
    }.start();
    while (true) {
      client.sendInfo();
    }
  }

}
