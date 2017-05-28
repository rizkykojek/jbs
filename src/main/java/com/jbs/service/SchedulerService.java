package com.jbs.service;

/**
 * Created by rizkykojek on 4/2/17.
 */
public interface SchedulerService {

    void populateEmployeeSF() throws Exception;

    void populateEmployeeSfOnStart() throws Exception;

    void populateEmployeeSfOnSchedule() throws Exception;

}
