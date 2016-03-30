package com.openreuse.server.request.route;

import com.openreuse.common.message.Message;
import com.openreuse.common.message.MessageType;
import com.openreuse.common.message.builder.MessageBuilder;
import com.openreuse.server.request.session.SessionManager;
import com.openreuse.server.response.ResponseHelper;
import com.openreuse.server.response.ResponseService;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by kimmin on 3/26/16.
 */
public class RegisterRoute implements Route {

    public boolean route(Message message){
        Long uid = SessionManager.getInstance().getUsrId(message.getFrom());
        if(null != uid){
            /** Just ignore it! Garbage Msg! **/
        }else{
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(
                    new ByteArrayInputStream(message.getBody().getBytes())));
            /** First line is username, second line is password **/
            try{
                String username = br.readLine();
                String password = br.readLine();
                long newUid = SessionManager.getInstance().registerUsr(username, password);
                if(newUid != -1){
                    Message resp = MessageBuilder.messageBuilder()
                            .setType(MessageType.REGISTER_MESSAGE)
                            .setBody(message.getFrom())
                            .setFrom("SERVER")
                            .setTo(message.getFrom())
                            .build();
//                    Channel channel = SessionManager.getInstance().getSession(uid);
                    ResponseService.getInstance().sendMessage(resp, message.getFrom());
                }else{
//                    ByteBuf buf = Unpooled.copiedBuffer(ResponseHelper.BYTE_ERROR_RESP_MESSAGE);
//                    Channel channel = SessionManager.getInstance().getSession(uid);
//                    channel.write(buf);
                    return false;
                }
                return true;
            }catch (IOException ioe){
                ioe.printStackTrace();
                return false;
            }
        }
        return false;
    }

}
