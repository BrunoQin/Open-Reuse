package com.openreuse.common.monitor.hook;


/**
 * Created by kimmin on 4/13/16.
 */
public class InfoStatusHook extends StatusHook {
    public InfoStatusHook(HookAction action){
        super(action);
        super.setPriority(0);
    }
}
