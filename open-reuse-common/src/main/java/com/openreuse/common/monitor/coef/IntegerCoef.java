package com.openreuse.common.monitor.coef;

import com.openreuse.common.monitor.coef.type.CoefType;

/**
 * Created by kimmin on 4/12/16.
 */
public class IntegerCoef extends AbstractCoef<Integer> {
    public IntegerCoef(int iVal){
        super(iVal);
        this.type = CoefType.INTEGER;
    }

    private CoefType type;

    public void setValue(int iVal){
        super.setValue(iVal);
    }

    public Integer getValue(){
        return super.getValue();
    }

}
