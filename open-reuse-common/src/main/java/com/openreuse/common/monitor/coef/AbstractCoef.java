package com.openreuse.common.monitor.coef;

import com.openreuse.common.monitor.coef.type.CoefType;

import java.io.Serializable;

/**
 * Created by kimmin on 4/11/16.
 */

public abstract class AbstractCoef<T> {

    public AbstractCoef(T value, CoefType type){
        this.value = value;
        this.type = type;
    }

    private T value;
    private CoefType type;

    public void setValue(T value){
        this.value = value;
    }

    public T getValue(){
        return this.value;
    }

    public CoefType getType(){
        return this.type;
    }

    public void setType(CoefType type){
        this.type = type;
    }

}
