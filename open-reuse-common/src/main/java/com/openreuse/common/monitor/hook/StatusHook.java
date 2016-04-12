package com.openreuse.common.monitor.hook;

import com.openreuse.common.monitor.Status;

import java.util.Comparator;

/**
 * Created by kimmin on 4/13/16.
 */
public abstract class StatusHook {

    public StatusHook(HookAction action){
        this.action = action;
    }

    public HookAction action;

    protected String ENABLE_MSG = "Successfully enabled hook!";
    protected String DISABLE_MSG = "Successfully disabled hook!";
    protected String INVOKE_MSG = "FAKE INVOCATON OF HOOK..";
    protected String INSTALL_MSG = "Successfully installed hook!";
    protected String REMOVE_MSG = "Successfully removed hook!";

    public StatusHook(){}

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    /** Default priority = 0 in [-INF, +INF] **/
    private int priority = 0;

    public void onEnabled(){
        System.out.println(this.ENABLE_MSG);
    }
    public void onDisabled(){
        System.out.println(this.DISABLE_MSG);
    }
    public void onInvoked(){
        action.whenHooked();
        System.out.println(this.INVOKE_MSG);
    }
    public void onInstalled(){
        System.out.println(this.INVOKE_MSG);
    }
    public void onRemoved(){
        System.out.println(this.INVOKE_MSG);
    }



}
