package com.ls.netty.protoctcp;

public class Protocol {
  private int len;
  private byte[] content;

  public Protocol() {
  }

  public Protocol(int len, byte[] content) {
    this.len = len;
    this.content = content;
  }

  public int getLen() {
    return len;
  }

  public void setLen(int len) {
    this.len = len;
  }

  public byte[] getContent() {
    return content;
  }

  public void setContent(byte[] content) {
    this.content = content;
  }
}
