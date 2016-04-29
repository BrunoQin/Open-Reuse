package com.openreuse.server.request.route;

import com.openreuse.common.message.Message;
import com.openreuse.common.message.MessageType;
import com.openreuse.common.message.builder.MessageBuilder;
import com.openreuse.server.group.Group;
import com.openreuse.server.group.GroupManager;
import com.openreuse.server.registry.RegistryManager;
import com.openreuse.server.request.session.SessionManager;
import com.openreuse.server.response.ResponseService;
import io.netty.channel.Channel;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * Created by kimmin on 4/29/16.
 */
public class GroupRoute implements Route {

    public boolean route(Message message){
        Long uid = SessionManager.getInstance().getUsrId(message.getFrom());
        if(null == uid){
            /** Just ignore it.. **/
            return false;
        }else{
            Message resp = MessageBuilder.messageBuilder()
                    .setBody(message.getBody())
                    .setType(MessageType.TEXT_MESSAGE_GROUP)
                    .setFrom("SERVER")
                    .build();
            try{
                Long gid = Long.parseLong(message.getReserved().getContent());
                Group group = GroupManager.getInstance().getGroupByGid(gid);
                if(group == null){
                   return false;
                }
                Iterator<String> iterator = group.userList.iterator();
                while(iterator.hasNext()){
                    String username = iterator.next();
                    resp.setTo(username);
                    ResponseService.getInstance().sendMessage(resp, username);
                }
            }catch (Throwable e){
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }
}
