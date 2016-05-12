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
public class UnicastResponseTask extends ResponseTask{

    public UnicastResponseTask(Message message, String username){
        super(message);
        this.username = username;
    }
    private String username;

    @Override
    public void run(){
        Iterator<Map.Entry<Long, Channel>> iter = SessionManager.getInstance().sessionIterator();
        while(iter.hasNext()){
            Map.Entry<Long, Channel> entry = iter.next();
            String to = super.getMessage().getTo();
            Long uid = SessionManager.getInstance().getUsrId(to);
            if(uid == null) continue;
            if(!entry.<Long>getKey().equals(uid)){
                continue;
            }
            try{
                byte[] bytes = super.om.writeValueAsBytes(super.getMessage());
                Channel channel = entry.getValue();
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
