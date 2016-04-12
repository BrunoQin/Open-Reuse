package com.openreuse.common.config.coef;

import com.openreuse.common.config.coef.type.CoefType;

/**
 * Created by kimmin on 4/12/16.
 */
public class StringCoef extends AbstractCoef<String> {

    public StringCoef(String value){
        super(value, CoefType.VARCHAR);
    }

    public void setValue(String szVal){
        super.setValue(szVal);
    }

    public String getValue(){
        return super.getValue();
    }
}
