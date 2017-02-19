package com.jbs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by rizkykojek on 2/18/17.
 */
@Controller
public class EmployeeController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getIndex() {
        return "index";
    }

    @RequestMapping(value = "/summary", method = RequestMethod.GET)
    public String getSummary() {
        return "summary";
    }

    @RequestMapping(value = "/event", method = RequestMethod.GET)
    public String getEvent() {
        return "event";
    }

}
