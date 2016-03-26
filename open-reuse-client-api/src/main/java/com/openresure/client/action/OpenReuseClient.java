package com.openresure.client.action;

import com.openresure.client.channel.BaseChannelInit;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;

/**
 * Created by Bruno on 16/3/23.
 */
public class OpenReuseClient {

    //端口与服务器ip
    private final String host;
    private final int port;

    private EventLoopGroup group;
    private Bootstrap bootstrap;
    private BaseChannelInit baseChannelInit;
    private Channel channel;

    public OpenReuseClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void connectServer() throws Exception{

        group = new NioEventLoopGroup();
        baseChannelInit = new BaseChannelInit();
        try {
            bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .remoteAddress(new InetSocketAddress(host, port))
                    .handler(baseChannelInit);

            ChannelFuture f = bootstrap.connect().sync();
            channel = f.channel();
            //f.channel().closeFuture().sync();
        } finally {
            //group.shutdownGracefully().sync();
        }

    }

    public void sendMessage(Object msg){
        ByteBuf massage;
        byte[] req = toByteArray(msg);
        massage = Unpooled.buffer(req.length);
        massage.writeBytes(req);
        channel.writeAndFlush(massage).addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if(future.isSuccess()){
                    System.out.println(111);
                }
            }
        });
    }

    /**
     * 对象转数组
     * @param obj
     * @return
     */
    public byte[] toByteArray (Object obj) {
        byte[] bytes = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            oos.flush();
            bytes = bos.toByteArray ();
            oos.close();
            bos.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return bytes;
    }

}
