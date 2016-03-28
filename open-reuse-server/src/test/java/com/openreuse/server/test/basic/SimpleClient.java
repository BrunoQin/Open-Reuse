package com.openreuse.server.test.basic;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import org.junit.After;
import org.junit.Test;

/**
 * Created by kimmin on 3/23/16.
 */


public class SimpleClient {

    public void connect(int port, String host) throws Exception {

        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel channel) throws Exception{
//                            channel.pipeline().addLast(new FixedLengthFrameDecoder(1024));
                            channel.pipeline().addLast(new SimpleClientHandler());
                        }
                    });
            ChannelFuture future = bootstrap.connect(host, port).sync();
            future.channel().closeFuture().sync();
        }catch (Throwable e){
            e.printStackTrace();
        }finally {
            group.shutdownGracefully();
        }
    }

}
