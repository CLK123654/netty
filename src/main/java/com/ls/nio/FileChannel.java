package com.ls.nio;

import org.junit.Test;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;

public class FileChannel {

  @Test
  public void testFileChannelWrite() throws FileNotFoundException {
    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
    byteBuffer.put("speak is too cheap,show me your code".getBytes());
    FileOutputStream fos = new FileOutputStream("test1.txt");
    java.nio.channels.FileChannel fileChannel = fos.getChannel();
    byteBuffer.flip();
    try {
      fileChannel.write(byteBuffer);
    } catch (IOException e) {
      e.printStackTrace();
    }finally {
      try {
        fos.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  @Test
  public void testFileRead() {
    FileInputStream fis = null;
    try {
      File file = new File("test1.txt");
      ByteBuffer byteBuffer = ByteBuffer.allocate((int) file.length());
      fis = new FileInputStream("test1.txt");
      java.nio.channels.FileChannel fileChannel = fis.getChannel();
      fileChannel.read(byteBuffer);
      System.out.println(new String(byteBuffer.array()));
    } catch (Exception e) {
      e.printStackTrace();
    }finally {
      try {
        fis.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
  
  @Test
  public void testMappedByteBuffer() {
    RandomAccessFile raf = null;
    try {
      raf = new RandomAccessFile("test1.txt", "rw");
      MappedByteBuffer mapBuffer = raf.getChannel().map(java.nio.channels.FileChannel.MapMode.READ_WRITE, 0, raf.length());
      mapBuffer.put(0, (byte)'S');
    } catch (Exception e) {
      e.printStackTrace();
    }finally {
      try {
        raf.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
