package com.ls.bio;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

/**
 * @author leofs
 */
public class Server {
  public static int PORT = 5000;
  public static ExecutorService service = new ThreadPoolExecutor(100, 200, 0L,
          TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(1024));

  public static void main(String[] args) throws Exception {
    ServerSocket serverSocket = new ServerSocket(PORT);
    System.out.println("Server  start.......");
    while (true) {
      Socket client = serverSocket.accept();
      System.out.println("A client " +client.getInetAddress().getHostName()+" connected!");
      service.execute(new Runnable() {
        @Override
        public void run() {
          receive(client);
        }
      });
    }
  }

  public static void receive(Socket socket) {
    while (true) {
      try {
        BufferedInputStream br = new BufferedInputStream(socket.getInputStream());
        int len = br.read();
        System.out.println("数据的长度:" + len);
        byte[] bytes = new byte[len];
        br.read(bytes);
        System.out.println(new String(bytes));
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}
