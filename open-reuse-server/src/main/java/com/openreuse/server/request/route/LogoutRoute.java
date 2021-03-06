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
public class LogoutRoute implements Route {

    private static ObjectMapper om = new ObjectMapper();

    public boolean route(Message message){
        Long uid = SessionManager.getInstance().getUsrId(message.getFrom());
        if (null == uid) {
            /** Just ignore it! Garbage Msg! **/
            return false;
        } else {
            Message resp = new Message(MessageType.LOGOUT_MESSAGE,
                    new Reserved("null"),
                    message.getFrom(),
                    "SERVER",
                    "MULTICAST");
            ResponseService.getInstance().sendMessage(resp);
            SessionManager.getInstance().removeSession(uid);
            return true;
        }
    }
}
