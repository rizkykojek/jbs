package com.jbs.config.security;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Created by rizkykojek on 4/29/17.
 */
public class SessionListener implements HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent event) {
        System.out.println("==== Session is created ====");
        event.getSession().setMaxInactiveInterval(10*60);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        System.out.println("==== Session is destroyed ====");
    }
}
