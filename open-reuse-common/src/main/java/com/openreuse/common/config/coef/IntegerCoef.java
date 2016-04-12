package com.openreuse.common.config.coef;

import com.openreuse.common.config.coef.type.CoefType;

/**
 * Created by kimmin on 4/12/16.
 */
public class IntegerCoef extends AbstractCoef<Integer> {
    public IntegerCoef(int iVal){
        super(iVal, CoefType.INTEGER);
    }

    public void setValue(int iVal){
        super.setValue(iVal);
    }

    public Integer getValue(){
        return super.getValue();
    }

}
