package com.openreuse.server.handler;

import com.openreuse.common.message.Message;
import com.openreuse.common.message.MessageType;
import com.openreuse.server.request.json.ParseJsonService;
import com.openreuse.server.request.session.SessionManager;
import com.openreuse.server.response.ResponseHelper;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.codehaus.jackson.map.ObjectMapper;

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
            if(message.getType() == MessageType.REGISTER_MESSAGE){
                ParseJsonService.getInstance().provideRawBytes(rawBytes);
                Long uid = SessionManager.getInstance().getUsrId(from);
                while(uid == null){
                    /** Waiting for register complete **/
                    try{
                        Thread.currentThread().sleep(500);
                    }catch (InterruptedException ie){
                        ie.printStackTrace();
                    }
                    uid = SessionManager.getInstance().getUsrId(from);
                }
                SessionManager.getInstance().registerSession(uid, ctx.channel());
                return;
            }
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
