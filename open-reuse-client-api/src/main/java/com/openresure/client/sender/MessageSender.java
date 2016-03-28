package com.openresure.client.sender;


import com.openresure.client.config.ConfigManager;
import com.openresure.client.service.MessageSendingService;
import com.openreuse.common.message.Message;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import org.codehaus.jackson.map.ObjectMapper;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;

/**
 * Created by min.jin on 2016/3/28.
 */
public class MessageSender implements Runnable{

    private static ObjectMapper om = new ObjectMapper();

    public MessageSender(Channel channel, Lock channelLock){
        this.currentChannel = channel;
        this.channelLock = channelLock;
    }

    private Channel currentChannel;
    private AtomicBoolean runFlag = new AtomicBoolean(true);
    private Lock channelLock;

    public void run(){
        while(runFlag.get()){
//            channelLock.lock();
            Message message = MessageSendingService.getInstance().consumeMessage();
            if(message == null){
//                channelLock.unlock();
                continue;
            }
            try{
                byte[] bytes = om.writeValueAsBytes(message);
                ByteBuf buf = Unpooled.copiedBuffer(bytes);
                ConfigManager.getInstance().getChannel().writeAndFlush(buf);
//                buf.release();
            }catch (Throwable e){
                e.printStackTrace();
            }
//            channelLock.unlock();
        }
    }
}
