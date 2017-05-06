package com.jbs.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.google.common.collect.Lists;
import com.jbs.entity.*;
import com.jbs.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by rizkykojek on 2/18/17.
 */
@Controller
public class EmployeeController {

    @Autowired
    private ShiftRepository shiftRepository;

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private EmployeeEventTableRepository employeeEventTableRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getIndex() {
        return "index";
    }

    @RequestMapping(value = "/summary/{employeeId}", method = RequestMethod.GET)
    public String getSummary(@PathVariable final Long employeeId, final Model model) {
        Employee employee = employeeRepository.findOne(employeeId);
        model.addAttribute(employee);
        return "summary";
    }

    @RequestMapping(value = "/duties", method = RequestMethod.GET)
    public String getDuties() {
        return "duties";
    }

    @RequestMapping(value = "/wcmc", method = RequestMethod.GET)
    public String getWcmc() {
        return "wcmc";
    }

    @RequestMapping(value = "/performance_report", method = RequestMethod.GET)
    public String getPerformanceReport() {
        return "performance_report";
    }

    @RequestMapping(value = "/event_report", method = RequestMethod.GET)
    public String getEventReport() {
        return "event_report";
    }

    @ModelAttribute("listShift")
    public List<Shift> getListShift() {
        return Lists.newArrayList(shiftRepository.findAll());
    }

    @ModelAttribute("listSection")
    public List<Section> getListSection() {
        return Lists.newArrayList(sectionRepository.findAll());
    }

    @ModelAttribute("listDepartment")
    public List<Department> getListDepartment() {
        return Lists.newArrayList(departmentRepository.findAll());
    }

    @JsonView(DataTablesOutput.View.class)
    @RequestMapping(value = "/hrClearanceTable", method = RequestMethod.GET)
    public @ResponseBody DataTablesOutput getHrClearanceTable(@Valid DataTablesInput request) {
        AtomicInteger atomicInteger = new AtomicInteger(request.getStart() + 1);
        DataTablesOutput<EmployeeEvent> results = employeeEventTableRepository.findAll(request);
        results.getData().stream().forEach(s -> s.setCounterNumber(atomicInteger.getAndIncrement()));
        return results;
    }

    @JsonView(DataTablesOutput.View.class)
    @RequestMapping(value = "/absentTable", method = RequestMethod.GET)
    public @ResponseBody DataTablesOutput getAbsentTable(@Valid DataTablesInput request) {
        AtomicInteger atomicInteger = new AtomicInteger(request.getStart() + 1);
        DataTablesOutput<EmployeeEvent> results = employeeEventTableRepository.findAll(request);
        results.getData().stream().forEach(s -> s.setCounterNumber(atomicInteger.getAndIncrement()));
        return results;
    }


}
