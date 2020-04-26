package com.ls.netty;

import io.netty.channel.socket.SocketChannel;

import java.util.ArrayList;
import java.util.List;

public class NettyChannelList {
  public static List<SocketChannel> list = new ArrayList<>();

  public static void add(SocketChannel socketChannel) {
    list.add(socketChannel);

  }

  public static void remove(SocketChannel socketChannel) {
    list.remove(socketChannel);
  }

  public static List<SocketChannel> getChannels() {
    return list;
  }
}
