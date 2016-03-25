package com.openreuse.server.handler;

import com.openreuse.common.message.Message;
import com.openreuse.server.request.json.ParseJsonService;
import com.openreuse.server.response.ResponseHelper;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by kimmin on 3/25/16.
 */

@ChannelHandler.Sharable
public class RawJsonHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx,
                            Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        byte[] rawBytes = buf.array();
        ParseJsonService.getInstance().provideRawBytes(rawBytes);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx){
        ctx.write(ResponseHelper.OK_RESP_MESSAGE);

        /**  **/
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){
        ctx.write(ResponseHelper.ERROR_RESP_MESSAGE);
        ctx.close();
    }

}
