package com.ls.bio;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client {
  public static void main(String[] args) throws IOException {
    Socket socket = new Socket("127.0.0.1", 5000);
    System.out.println("client start ....");
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream());
    try {
      while (true) {
        String readLine = br.readLine();
        int len = readLine.getBytes().length;
        byte[] dataLen = {(byte)len};
        bos.write(dataLen,0, 1);
        bos.write(readLine.getBytes());
        bos.flush();
      }
    }catch (Exception e){
      e.printStackTrace();
    }finally {
      bos.close();
      br.close();
    }
  }
}
