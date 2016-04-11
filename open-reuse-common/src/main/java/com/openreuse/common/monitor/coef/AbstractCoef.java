package com.openreuse.common.monitor.coef;

import com.openreuse.common.monitor.coef.type.CoefType;

import java.io.Serializable;

/**
 * Created by kimmin on 4/11/16.
 */
public abstract class AbstractCoef<T> {

    public AbstractCoef(T value){

        this.value = value;

    }

    private T value;

    public void setValue(T value){
        this.value = value;
    }

    public T getValue(){
        return this.value;
    }

}
