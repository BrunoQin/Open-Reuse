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

import java.util.concurrent.CountDownLatch;

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
    private static boolean isConnected = false;

    public Thread connThread = null;
    public CountDownLatch latch = new CountDownLatch(1);


    public boolean doConnect(String ipAddr){
        connThread = new Thread(new ConnRunner(ipAddr));
        connThread.start();
        long now = System.currentTimeMillis();
        while(now + 2 * 1000 > System.currentTimeMillis()){
            if(isConnected) return true;
        }
        return false;
    }

    public class ConnRunner implements Runnable {

        public ConnRunner(String ipAddr){
            this.ipAddr = ipAddr;
        }

        private String ipAddr;

        public void run(){
            EventLoopGroup group = new NioEventLoopGroup();
            Bootstrap bootstrap = new Bootstrap();
            try{
                bootstrap.group(group)
                        .channel(NioSocketChannel.class)
                        .option(ChannelOption.TCP_NODELAY, true)
                        .option(ChannelOption.SO_KEEPALIVE, true)
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
                latch.countDown();
                isConnected = true;
                /** Confirm Configuration **/
                future.channel().closeFuture().sync();
            }catch (InterruptedException ie){
                ie.printStackTrace();
            }finally {
                group.shutdownGracefully();
            }
        }
    }

}
