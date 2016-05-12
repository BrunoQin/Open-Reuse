package com.openreuse.server.response;

import com.openreuse.common.message.Message;
import com.openreuse.server.request.session.SessionManager;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by min.jin on 2016/3/28.
 */
public class MulticastResponseTask extends ResponseTask {
    public MulticastResponseTask(Message message){
        super(message);
    }

    @Override
    public void run(){
        Iterator<Map.Entry<Long, Channel>> iter = SessionManager.getInstance().sessionIterator();
        while(iter.hasNext()){
            try{
                byte[] bytes = super.om.writeValueAsBytes(super.getMessage());
                Channel channel = iter.next().getValue();
                if(channel == null){
                    ResendMsgService.getInstance().addFailedMsg(getMessage());
                }else {
                    ByteBuf buf = Unpooled.copiedBuffer(bytes);
                    channel.writeAndFlush(buf);
                }
            }catch (IOException ioe){
                ioe.printStackTrace();
            }
        }
    }
}
