package com.jbs.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.google.common.collect.Lists;
import com.jbs.entity.Department;
import com.jbs.entity.Section;
import com.jbs.entity.Shift;
import com.jbs.repository.DepartmentRepository;
import com.jbs.repository.EmployeeEventTableRepository;
import com.jbs.repository.SectionRepository;
import com.jbs.repository.ShiftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.List;

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

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getIndex() {
        return "index";
    }

    @RequestMapping(value = "/summary", method = RequestMethod.GET)
    public String getSummary() {
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
    public @ResponseBody
    DataTablesOutput getHrClearanceTable(@Valid DataTablesInput request) {

        return employeeEventTableRepository.findAll(request);
    }

    @JsonView(DataTablesOutput.View.class)
    @RequestMapping(value = "/absentTable", method = RequestMethod.GET)
    public @ResponseBody
    DataTablesOutput getAbsentTable(@Valid DataTablesInput request) {

        return employeeEventTableRepository.findAll(request);
    }


}
