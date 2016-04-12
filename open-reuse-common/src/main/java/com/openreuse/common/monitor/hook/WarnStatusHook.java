package com.openreuse.common.monitor.hook;

/**
 * Created by kimmin on 4/13/16.
 */
public class WarnStatusHook extends StatusHook {
    public WarnStatusHook(HookAction action){
        super(action);
        super.setPriority(50);
    }
}
