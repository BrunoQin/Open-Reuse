package com.openreuse.common.monitor.hook;

/**
 * Created by kimmin on 4/13/16.
 */
public class ErrorStatusHook extends StatusHook {
    public ErrorStatusHook(HookAction action) {
        super(action);
        super.setPriority(100);
    }
}

