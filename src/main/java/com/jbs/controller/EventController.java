package com.jbs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by rizkykojek on 3/21/17.
 */

@Controller
public class EventController {

    @RequestMapping(value = "/event", method = RequestMethod.GET)
    public String getEvent() {
        return "event";
    }

}
