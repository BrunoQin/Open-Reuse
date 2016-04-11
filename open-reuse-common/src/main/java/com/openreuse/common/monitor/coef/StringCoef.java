package com.openreuse.common.monitor.coef;

import com.openreuse.common.monitor.coef.type.CoefType;

/**
 * Created by kimmin on 4/12/16.
 */
public class StringCoef extends AbstractCoef<String> {

    public StringCoef(String value){
        super(value);
        this.type = CoefType.VARCHAR;
    }

    private CoefType type;

    public void setValue(String szVal){
        super.setValue(szVal);
    }

    public String getValue(){
        return super.getValue();
    }
}
