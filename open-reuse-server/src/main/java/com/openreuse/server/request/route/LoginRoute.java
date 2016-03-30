package com.openreuse.server.request.route;

import com.openreuse.common.message.Message;
import com.openreuse.common.message.MessageType;
import com.openreuse.common.message.Reserved;
import com.openreuse.server.registry.RegistryManager;
import com.openreuse.server.request.session.SessionManager;
import com.openreuse.server.response.ResponseHelper;
import com.openreuse.server.response.ResponseService;
import com.openreuse.server.throttle.ThrottleStatsManager;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;

/**
 * Created by kimmin on 3/26/16.
 */
public class LoginRoute implements Route{

    private static ObjectMapper om = new ObjectMapper();

    public boolean route(Message message) {
        Long uid = SessionManager.getInstance().getUsrId(message.getFrom());
        if (null == uid) {
            /** Just ignore it! Garbage Msg! **/
            return false;
        } else {
            Channel channel = SessionManager.getInstance().getSession(uid);
            boolean success = RegistryManager.getInstance().checkLogin(uid);
            ByteBuf buf;
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(
                    new ByteArrayInputStream(message.getBody().getBytes())));
            boolean validSuccess = false;
            try {
                String username = br.readLine();
                String password = br.readLine();
                validSuccess = SessionManager.getInstance()
                        .validUsrAndPass(username, password);
            }catch (Throwable e){
                e.printStackTrace();
            }
            if(!(success && validSuccess)){
                buf = Unpooled.copiedBuffer(ResponseHelper.BYTE_ERROR_RESP_MESSAGE);
                channel.writeAndFlush(buf);
                /** Remove channel **/
                SessionManager.getInstance().removeSession(uid);
                ThrottleStatsManager.getInstance().incInvalidLoginCount();
                return false;
            }
            Message resp = new Message(MessageType.LOGIN_MESSAGE,
                    new Reserved("null"),
                    message.getFrom(),
                    "SERVER",
                    "MULTICAST");
            ThrottleStatsManager.getInstance().clrMsgCount(uid);
            ResponseService.getInstance().sendMessage(resp);
            RegistryManager.getInstance().registerLogin(uid);
            ThrottleStatsManager.getInstance().incValidLoginCount();
            return true;
        }
    }
}

