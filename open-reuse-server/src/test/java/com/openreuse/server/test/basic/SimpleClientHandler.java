package com.openreuse.server.test.basic;

import com.openreuse.server.response.ResponseHelper;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelOutboundHandlerAdapter;

/**
 * Created by kimmin on 3/23/16.
 */

public class SimpleClientHandler extends ChannelInboundHandlerAdapter {

    private final ByteBuf message;

    public SimpleClientHandler() {
        byte[] req = ResponseHelper.BYTE_OK_RESP_MESSAGE;
        message = Unpooled.buffer(req.length);
        message.writeBytes(req);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(message);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception{
        ByteBuf buf = (ByteBuf) msg;
        byte[] req = new byte[buf.readableBytes()];
        buf.readBytes(req);
        String body = new String(req, "UTF-8");
        System.out.println("COMING MESG:" + body);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable e){
        ctx.close();
    }

}

