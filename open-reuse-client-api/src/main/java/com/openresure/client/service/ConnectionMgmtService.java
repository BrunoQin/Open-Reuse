package com.openresure.client.service;

import com.openresure.client.config.ConfigManager;
import com.openresure.client.handler.IncomingMessageHandler;
import com.openreuse.server.misc.Constants;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;

/**
 * Created by min.jin on 2016/3/28.
 */
public class ConnectionMgmtService {
    private  ConnectionMgmtService(){}
    private static class Singleton{
        private static ConnectionMgmtService instance = new ConnectionMgmtService();
    }
    public static ConnectionMgmtService getInstance(){
        return Singleton.instance;
    }

    private EventLoopGroup group = new NioEventLoopGroup();
    private Bootstrap bootstrap = new Bootstrap();
    private Thread connThread = null;

    public boolean doConnect(String ipAddr){

        try{
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel channel) throws Exception{
//                            channel.pipeline().addLast(new FixedLengthFrameDecoder(1024));
                            channel.pipeline().addLast(new IncomingMessageHandler());
                        }
                    });
            ChannelFuture future = bootstrap.connect(ipAddr, Constants.SERVER_PORT).sync();
            assert future.isSuccess();
            ConfigManager.getInstance().setChannel(future.channel());
            ConfigManager.getInstance().setCurrentServerAddr(ipAddr);
            ConfigManager.getInstance().setChannelFuture(future);
            /** Confirm Configuration **/
            return true;
        }catch (InterruptedException ie){
            ie.printStackTrace();
            return false;
        }
    }
}
