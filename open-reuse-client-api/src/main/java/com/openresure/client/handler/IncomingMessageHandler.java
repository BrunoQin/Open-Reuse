package com.openresure.client.handler;

import com.openresure.client.config.ConfigManager;
import com.openresure.client.service.MessageNotifyService;
import com.openreuse.common.message.Message;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * Created by min.jin on 2016/3/28.
 */

@ChannelHandler.Sharable
public class IncomingMessageHandler extends ChannelInboundHandlerAdapter {

    private static ObjectMapper om = new ObjectMapper();

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ConfigManager.getInstance().setChannel(
                ctx.channel()
        );
        ctx.fireChannelActive();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception{
        ConfigManager.getInstance().setChannel(ctx.channel());
        try{
            ByteBuf buf = Unpooled.copiedBuffer((ByteBuf) msg);
            Message message = om.readValue(buf.array(), Message.class);
            System.out.println(message.getType().getType());
            MessageNotifyService.getInstance().publish(message);
        }catch (Throwable e){
            e.printStackTrace();
        }
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable e){
        ctx.close();
    }

}
