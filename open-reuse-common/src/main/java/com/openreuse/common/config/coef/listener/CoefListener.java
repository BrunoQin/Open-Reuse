package com.openreuse.common.config.coef.listener;

import com.openreuse.common.config.coef.AbstractCoef;

/**
 * Created by kimmin on 4/12/16.
 */
public interface CoefListener<T extends AbstractCoef> {
    void onCoefChanged(T t);
}
