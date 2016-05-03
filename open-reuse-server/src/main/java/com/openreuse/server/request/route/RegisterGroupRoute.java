package com.openreuse.server.request.route;

import com.openreuse.common.message.Message;
import com.openreuse.common.message.MessageType;
import com.openreuse.common.message.builder.MessageBuilder;
import com.openreuse.server.group.Group;
import com.openreuse.server.group.GroupIdPool;
import com.openreuse.server.group.GroupManager;
import com.openreuse.server.response.ResponseService;

/**
 * Created by kimmin on 5/3/16.
 */
public class RegisterGroupRoute implements Route {
    public boolean route(Message message){
        String groupName = message.getReserved().getContent().trim();
        String[] members = message.getBody().split("\n");
        long newGid = GroupIdPool.allocateGroupId();
        Group group = new Group(newGid);
        for(String member: members){
            group.userList.add(member);
        }
        GroupManager.getInstance().enableGroup(newGid, group);
        StringBuilder sb = new StringBuilder();
        sb.append(groupName);
        sb.append("\n");
        sb.append(Long.toString(newGid));
        Message resp  = MessageBuilder.messageBuilder()
                .setBody(sb.toString())
                .setType(MessageType.RESP_REGISTER_GROUP_OK)
                .setFrom("SERVER")
                .setTo(message.getFrom())
                .build();
        ResponseService.getInstance().sendMessage(resp, message.getFrom());
        return true;
    }
}
