package com.openreuse.server.request.route;

import com.openreuse.common.message.Message;
import com.openreuse.common.message.MessageType;
import com.openreuse.common.message.Reserved;
import com.openreuse.server.request.session.SessionManager;
import com.openreuse.server.response.ResponseHelper;
import com.openreuse.server.throttle.ThrottleStatsManager;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import org.codehaus.jackson.map.ObjectMapper;
import org.hibernate.Session;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by kimmin on 3/26/16.
 */
public class TextRoute implements Route{

    private static ObjectMapper om = new ObjectMapper();

    public boolean route(Message message){
        try {
            Long uid = SessionManager.getInstance().getUsrId(message.getFrom());
            assert uid != null;
            ThrottleStatsManager.getInstance().incMsgCount(uid);
            Message resp = new Message(MessageType.TEXT_MESSAGE,
                    new Reserved("null"),
                    message.getBody(),
                    "SERVER");
            byte[] bytes = om.writeValueAsBytes(resp);
            ByteBuf buf = Unpooled.copiedBuffer(bytes);
            for (Iterator<Map.Entry<Long, Channel>> iter = SessionManager.getInstance().sessionIterator();
                 iter.hasNext(); ) {
                Map.Entry<Long, Channel> entry = iter.next();
                entry.getValue().write(buf);
                ThrottleStatsManager.getInstance().incForwardedMsgCount();
            }
            if(!ThrottleStatsManager.getInstance().checkMsgCount(uid)) {
                Channel channel = SessionManager.getInstance().getSession(uid);
                ByteBuf bufResp = Unpooled.copiedBuffer(ResponseHelper.BYTE_REDO_LOGIN_RESP_MESSAGE);
                channel.write(bufResp);
            }
            return true;
        }catch (Throwable e){
            e.printStackTrace();
            return false;
        }
    }
}
