package com.openreuse.common.license.auth;

import com.openreuse.common.license.License;

/**
 * Created by kimmin on 4/13/16.
 */
public interface Processed {
    boolean getAuthStatus();
    License getLicense();
}
