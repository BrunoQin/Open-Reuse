package com.openreuse.server.test.basic;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Created by kimmin on 3/25/16.
 */
public class SimpleMessageClient {

    public void connect(int port, String host){

        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel channel) throws Exception{
//                            channel.pipeline().addLast(new FixedLengthFrameDecoder(1024));
                            channel.pipeline().addLast(new SimpleMessageHandler());
                        }
                    });
            ChannelFuture future = bootstrap.connect(host, port).sync();
        }catch (Throwable e){
            e.printStackTrace();
        }finally {
            //group.shutdownGracefully();
        }
    }

    public static void main(String[] args){
        SimpleMessageClient simpleMessageClient = new SimpleMessageClient();
        simpleMessageClient.connect(30000, "localhost");
    }

}
