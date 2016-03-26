package com.openresure.client.channel;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;

/**
 * Created by Bruno on 16/3/24.
 */
public class BaseChannelInit extends ChannelInitializer<Channel> {

    @Override
    protected void initChannel(Channel ch) throws Exception {

        ch.pipeline().addLast(new RecieveHandler());

    }

}
