package com.openresure.client.listener;

import com.openresure.client.group.ClientGroupManager;

/**
 * Created by kimmin on 5/3/16.
 */
public class ValidateRegisterGroupListener implements MessageListener {
    public void onMessageArrive(String body){
        /** Response message body contains long-type group Id **/
        String[] nameNid = body.trim().split("\n");
        String groupName = nameNid[0];

        long gid = Long.parseLong(nameNid[1]);
        if(gid != -1){
            ClientGroupManager.getInstance().putGroupId(groupName, gid);
        }else{
            /** Do nothing **/
        }
    }
    public boolean isValid(){
        return true;
    }
}
