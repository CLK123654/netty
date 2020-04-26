package com.ls.netty.rpc.customer;

import com.ls.netty.rpc.common.HelloService;

public class ClientBootStap {

  public static void main(String[] args) throws InterruptedException {
     Client client = new Client();
     HelloService service = client.getClientProxy(HelloService.class);
     String ret = service.Hello("xiao hong");
     System.out.println(ret);
  }
}
