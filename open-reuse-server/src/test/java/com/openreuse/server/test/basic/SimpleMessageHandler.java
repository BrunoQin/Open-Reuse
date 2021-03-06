package com.openreuse.server.test.basic;

import com.openreuse.common.message.Message;
import com.openreuse.common.message.MessageType;
import com.openreuse.common.message.builder.MessageBuilder;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.codehaus.jackson.map.ObjectMapper;


/**
 * Created by kimmin on 3/25/16.
 */

public class SimpleMessageHandler extends ChannelInboundHandlerAdapter {


    public static ByteBuf amessage;

    public static Channel achannel = null;

    public SimpleMessageHandler() {
        ObjectMapper om = new ObjectMapper();
        Message message = MessageBuilder.messageBuilder()
                .setBody("Bruno" + "\n" + "qinbo")
                .setType(MessageType.LOGIN_MESSAGE)
                .setFrom("Bruno")
                .build();
        /** Try JSON-Serialization here **/
        try{
            byte[] bytes = om.writeValueAsBytes(message);
            String str = new String(bytes);
            System.out.println(str);
            ByteBuf buf = Unpooled.copiedBuffer(bytes);
            amessage = buf;
        }catch (Throwable e){
            e.printStackTrace();
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        achannel = ctx.channel();

        //SimpleMessageHandler.achannel.write(SimpleMessageHandler.amessage);

        ctx.writeAndFlush(SimpleMessageHandler.amessage);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception{
        ByteBuf buf = (ByteBuf) msg;
        buf = Unpooled.copiedBuffer(buf);
        byte[] rawBytes = buf.array();
        System.out.println(rawBytes);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable e){
        ctx.close();
    }


}
