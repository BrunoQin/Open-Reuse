package com.openreuse.server.request.route;

import com.openreuse.common.message.Message;
import com.openreuse.server.request.session.SessionManager;
import com.openreuse.server.response.ResponseHelper;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * Created by kimmin on 3/26/16.
 */
public class RegisterRoute implements Route {

    public boolean route(Message message){
        Long uid = SessionManager.getInstance().getUsrId(message.getFrom());
        if(null == uid){
            /** Just ignore it! Garbage Msg! **/
        }else{
            Channel channel = SessionManager.getInstance().getSession(uid);
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(
                    new ByteArrayInputStream(message.getBody().getBytes())));
            /** First line is username, second line is password **/
            try{
                String username = br.readLine();
                String password = br.readLine();
                boolean success = SessionManager.getInstance().registerUsr(username, password);
                if(success){
                    ByteBuf buf = Unpooled.copiedBuffer(ResponseHelper.BYTE_OK_RESP_MESSAGE);
                    channel.write(buf);
                }else{
                    ByteBuf buf = Unpooled.copiedBuffer(ResponseHelper.BYTE_ERROR_RESP_MESSAGE);
                    channel.write(buf);
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
