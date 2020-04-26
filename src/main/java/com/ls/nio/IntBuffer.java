package com.ls.nio;

import java.nio.DoubleBuffer;

public class IntBuffer {

  public static void main(String[] args) {
  java.nio.IntBuffer buffer = java.nio.IntBuffer.allocate(5);
    buffer.flip();
  }

}
