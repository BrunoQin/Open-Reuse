package com.openreuse.server;

import com.openreuse.server.handler.RawJsonHandler;
import com.openreuse.server.misc.Constants;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Created by kimmin on 3/20/16.
 */
public class ServerApplication {

    public static void main(String[] args){

        /** Setting up NETTY server **/
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();


        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel channel) throws Exception{
//                      channel.pipeline().addLast(new FixedLengthFrameDecoder(1024));
                        channel.pipeline().addLast(new RawJsonHandler());
                    }
                });
        try {
            ChannelFuture future = bootstrap.bind(Constants.SERVER_PORT).sync();
            future.channel().closeFuture().sync();
        }catch (Throwable e){
            e.printStackTrace();
        }finally{
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

}
