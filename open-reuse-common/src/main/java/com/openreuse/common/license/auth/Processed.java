package com.openreuse.common.license.auth;

import com.openreuse.common.license.License;
import com.openreuse.common.license.exception.InvalidLicenseException;

/**
 * Created by kimmin on 4/13/16.
 */
public interface Processed {
    License getLicense();
    void suicide();
    void process() throws InvalidLicenseException;
}
