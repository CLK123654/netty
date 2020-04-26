package com.ls.netty.inandout;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;

public class ClientInitializer extends ChannelInitializer {
  @Override
  protected void initChannel(Channel channel) throws Exception {
    ChannelPipeline pipeline = channel.pipeline();
    pipeline.addLast(new MyEncoder());
    pipeline.addLast(new ClientHandler());
  }
}
