package com.jbs.config.security;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

/**
 * Created by rizkykojek on 4/29/17.
 */
public class SecurityWebAppInitializer extends AbstractSecurityWebApplicationInitializer {

    public SecurityWebAppInitializer() {
        super(SecurityConfig.class);
    }
}
