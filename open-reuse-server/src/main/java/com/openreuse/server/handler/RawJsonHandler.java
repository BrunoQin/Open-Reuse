package com.openreuse.server.handler;

import com.openreuse.common.message.Message;
import com.openreuse.server.request.json.ParseJsonService;
import com.openreuse.server.request.session.SessionManager;
import com.openreuse.server.response.ResponseHelper;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import org.codehaus.jackson.map.ObjectMapper;

import java.nio.channels.SeekableByteChannel;

/**
 * Created by kimmin on 3/25/16.
 */

@ChannelHandler.Sharable
public class RawJsonHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.fireChannelActive();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        buf = Unpooled.copiedBuffer(buf);
        byte[] rawBytes = buf.array();
        /** Check if session stored in the sessionMap, **/
        if(! SessionManager.getInstance().haveSession(ctx.channel())) {
            /** if not unpack the json on the scene **/
            ObjectMapper om = new ObjectMapper();
            Message message = om.readValue(rawBytes, Message.class);
            String from = message.getFrom();
            Long uid = SessionManager.getInstance().getUsrId(from);
            if(null == uid) return; /** Just ignore the msg **/
            SessionManager.getInstance().registerSession(uid, ctx.channel());
        }
        ParseJsonService.getInstance().provideRawBytes(rawBytes);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx){
        ctx.write(ResponseHelper.OK_RESP_MESSAGE);
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){
        ctx.write(ResponseHelper.ERROR_RESP_MESSAGE);
        ctx.close();
    }

}
