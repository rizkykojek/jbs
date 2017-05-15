package com.jbs.util;

import com.jbs.config.security.UserApplication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Created by rizkykojek on 5/6/17.
 */
public final class UserSessionUtil {

    public static Long getEmployeeId() {
        return ((UserApplication) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getEmployeeId();
    }

    public static String getFullName() {
        return ((UserApplication) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getFullName();
    }
}
