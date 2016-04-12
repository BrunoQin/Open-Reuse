package com.openreuse.common.monitor.hook;

import java.util.Comparator;

/**
 * Created by kimmin on 4/13/16.
 */

public class HookComparator implements Comparator<StatusHook> {

    public int compare(StatusHook hook1, StatusHook hook2){
        if(hook1.getPriority() < hook2.getPriority()){
            return -1;
        }else if(hook1.getPriority() > hook2.getPriority()){
            return 1;
        }else{
            return 0;
        }
    }

}