package com.jbs.service.impl;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.jbs.entity.*;
import com.jbs.repository.*;
import com.jbs.service.SchedulerService;
import com.jbs.util.ODataUtil;
import org.apache.olingo.odata2.api.edm.Edm;
import org.apache.olingo.odata2.api.ep.entry.ODataEntry;
import org.apache.olingo.odata2.api.ep.feed.ODataFeed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

/**
 * Created by rizkykojek on 4/2/17.
 */
@Service
public class SchedulerServiceImpl implements SchedulerService {

    @Autowired
    private Edm edm;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeEventRepository employeeEventRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private ShiftRepository shiftRepository;

    @Scheduled(fixedDelay = 1000000000, initialDelay = 1)
    @Transactional
    public void employeeDetails() throws Exception {
        ODataFeed feed = ODataUtil.readFeed(edm, "PerPersonal");
        List<Department> departments = Lists.newArrayList(departmentRepository.findAll());
        List<Position> positions = Lists.newArrayList(positionRepository.findAll());
        List<Section> sections = Lists.newArrayList(sectionRepository.findAll());
        List<Shift> shifts = Lists.newArrayList(shiftRepository.findAll());
        Random randomizer = new Random();
        String hrClearance[] = new String[]{"FW","AM","WR","SA","SR"};
        String absent[] = new String[]{"AC","AB","AA","UA"};
        int i = 0;

        for (ODataEntry entry: feed.getEntries()) {
            Gson gson = new Gson();
            JsonElement jsonElement = gson.toJsonTree(entry.getProperties());
            Employee employee = gson.fromJson(jsonElement, Employee.class);
            if  (employee.getFirstName() != null && employee.getLastName() != null && employee.getEmployeeNumber() != null) {
                employee.setDepartment(departments.get(randomizer.nextInt(departments.size())));
                employee.setPosition(positions.get(randomizer.nextInt(positions.size())));
                employee.setSection(sections.get(randomizer.nextInt(sections.size())));
                employee.setShift(shifts.get(randomizer.nextInt(shifts.size())));
                employeeRepository.save(employee);

                int eventType = (i % 2 == 0) ? 1 : 2;
                EmployeeEvent event = new EmployeeEvent();
                event.setEmployee(employee);
                event.setEventType(eventType);
                event.setEventName(eventType == 1 ? hrClearance[randomizer.nextInt(hrClearance.length)] : absent[randomizer.nextInt(absent.length)]);
                employeeEventRepository.save(event);
                i++;
            }
        }
    }
}
