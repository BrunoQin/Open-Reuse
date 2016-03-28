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

import java.util.Iterator;
import java.util.Map;

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
            if(!success){
                buf = Unpooled.copiedBuffer(ResponseHelper.BYTE_ERROR_RESP_MESSAGE);
                channel.write(buf);
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

