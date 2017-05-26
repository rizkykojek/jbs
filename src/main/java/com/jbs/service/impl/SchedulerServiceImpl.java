package com.jbs.service.impl;

import com.jbs.entity.Employee;
import com.jbs.entity.Site;
import com.jbs.repository.EmployeeRepository;
import com.jbs.repository.SiteRepository;
import com.jbs.service.SchedulerService;
import com.jbs.util.ODataUtil;
import org.apache.olingo.odata2.api.edm.Edm;
import org.apache.olingo.odata2.api.ep.entry.ODataEntry;
import org.apache.olingo.odata2.api.ep.feed.ODataDeltaFeed;
import org.apache.olingo.odata2.api.ep.feed.ODataFeed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Optional;

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
    private SiteRepository siteRepository;

    @Scheduled(fixedDelay = 1000000000, initialDelay = 1)
    @Transactional
    public void employeeDetails() throws Exception {
        Site site = siteRepository.findOne(50001l);
        ODataFeed perPerson = ODataUtil.readFeed(edm, "PerPerson","$expand=employmentNav,personalInfoNav,personalInfoNav/salutationNav");

        for (ODataEntry person: perPerson.getEntries()) {
            String personIdExternal = (String) person.getProperties().get("personIdExternal");
            System.out.println("process " + personIdExternal);
            Employee employee = employeeRepository.findByPersonIdExternal(personIdExternal);
            if (employee == null) {
                employee = new Employee();
                employee.setPersonIdExternal(personIdExternal);
            }

            Optional<Calendar> dateOfBirth = Optional.ofNullable((Calendar) person.getProperties().get("dateOfBirth"));
            employee.setDateOfBirth(dateOfBirth.isPresent() ? dateOfBirth.get().getTime() : null);

            Optional<ODataDeltaFeed> employmentNav = Optional.ofNullable((ODataDeltaFeed) person.getProperties().get("employmentNav"));
            Optional<ODataDeltaFeed> personalInfoNav = Optional.ofNullable((ODataDeltaFeed) person.getProperties().get("personalInfoNav"));
            if (!employmentNav.isPresent() || employmentNav.get().getEntries().size() == 0 || !personalInfoNav.isPresent() || personalInfoNav.get().getEntries().size() == 0) {
                continue;
            }

            Optional<ODataEntry> employment = employmentNav.get().getEntries().stream()
                    .sorted((e1,e2) -> ((Calendar) e2.getProperties().get("endDate")).compareTo(((Calendar) e1.getProperties().get("startDate"))))
                    .findFirst();
            if (employment.isPresent()) {
                String userId = (String) employment.get().getProperties().get("userId");
                employee.setUserId(userId);

                ODataFeed empJob = ODataUtil.readFeed(edm, "EmpJob","$expand=departmentNav,positionNav,locationNav,shiftCodeNav,customString5Nav,customString15Nav&$filter=userId%20eq%20%27"+ userId +"%27");
                Optional<ODataEntry> jobInfo = empJob.getEntries().stream()
                        .sorted((e1,e2) -> ((Calendar) e2.getProperties().get("endDate")).compareTo(((Calendar) e1.getProperties().get("startDate"))))
                        .findFirst();
                if (jobInfo.isPresent()) {
                    employee = setDepartment(employee, jobInfo);
                    employee = setPosition(employee, jobInfo);
                    employee = setShift(employee, jobInfo);
                    employee = setLocation(employee, jobInfo);
                    employee = setSection(employee, jobInfo);
                    employee = setPlant(employee, jobInfo);
                }
            }

            Optional<ODataEntry> personalInfo = personalInfoNav.get().getEntries().stream()
                    .sorted((e1,e2) -> ((Calendar) e2.getProperties().get("endDate")).compareTo(((Calendar) e1.getProperties().get("startDate"))))
                    .findFirst();
            if (personalInfo.isPresent()) {
                Optional<String> firstName = Optional.ofNullable((String) personalInfo.get().getProperties().get("firstName"));
                employee.setFirstName(firstName.isPresent() ? firstName.get() : null);

                Optional<String> lastName = Optional.ofNullable((String) personalInfo.get().getProperties().get("lastName"));
                employee.setLastName(lastName.isPresent() ? lastName.get() : null);

                Optional<String> middleName = Optional.ofNullable((String) personalInfo.get().getProperties().get("middleName"));
                employee.setMiddleName(middleName.isPresent() ? middleName.get() : null);

                Optional<String> gender = Optional.ofNullable((String) personalInfo.get().getProperties().get("gender"));
                employee.setGender(gender.isPresent() ? gender.get() : null);

                Optional<ODataDeltaFeed> salutationNav = Optional.ofNullable((ODataDeltaFeed) person.getProperties().get("salutationNav"));
                if (salutationNav.isPresent()) {
                    ODataEntry salutationEntry = salutationNav.get().getEntries().get(0);
                    employee.setSalutation((String) salutationEntry.getProperties().get("externalCode"));
                } else {
                    employee.setSalutation(null);
                }
            }

            employee.setSite(site);
            employeeRepository.save(employee);
        }

    }

    private Employee setDepartment(Employee employee, Optional<ODataEntry> jobInfo) {
        Optional<ODataEntry> department = Optional.ofNullable((ODataEntry) jobInfo.get().getProperties().get("departmentNav"));
        if (department.isPresent()) {
            employee.setDepartmentId((String) department.get().getProperties().get("externalCode"));
            employee.setDepartmentName((String) department.get().getProperties().get("name_en_GB"));
        } else {
            employee.setDepartmentId(null);
            employee.setDepartmentName(null);
        }
        return employee;
    }

    private Employee setPosition(Employee employee, Optional<ODataEntry> jobInfo) {
        Optional<ODataEntry> position = Optional.ofNullable((ODataEntry) jobInfo.get().getProperties().get("positionNav"));
        if (position.isPresent()) {
            employee.setPositionId((String) position.get().getProperties().get("code"));
            employee.setPositionName((String) position.get().getProperties().get("name_en_GB"));
        } else {
            employee.setPositionId(null);
            employee.setPositionName(null);
        }
        return employee;
    }

    private Employee setShift(Employee employee, Optional<ODataEntry> jobInfo) {
        Optional<ODataEntry> shift = Optional.ofNullable((ODataEntry) jobInfo.get().getProperties().get("shiftCodeNav"));
        if (shift.isPresent()) {
            employee.setShiftId((String) shift.get().getProperties().get("externalCode"));
            switch (employee.getShiftId()) {
                case "1":
                    employee.setShiftName("Day Shift");
                    break;
                case "2":
                    employee.setShiftName("Afternoon Shift");
                    break;
                case "3":
                    employee.setShiftName("Night Shift");
                    break;
            }
        } else {
            employee.setShiftId(null);
            employee.setShiftName(null);
        }
        return employee;
    }

    private Employee setLocation(Employee employee, Optional<ODataEntry> jobInfo) {
        Optional<ODataEntry> location = Optional.ofNullable((ODataEntry) jobInfo.get().getProperties().get("locationNav"));
        if (location.isPresent()) {
            employee.setLocationId((String) location.get().getProperties().get("externalCode"));
            employee.setLocationName((String) location.get().getProperties().get("name"));
        } else {
            employee.setLocationId(null);
            employee.setLocationName(null);
        }
        return employee;
    }

    private Employee setSection(Employee employee, Optional<ODataEntry> jobInfo) {
        Optional<ODataEntry> section = Optional.ofNullable((ODataEntry) jobInfo.get().getProperties().get("customString5Nav"));
        if (section.isPresent()) {
            employee.setSectionId((String) section.get().getProperties().get("externalCode"));
            employee.setSectionName((String) section.get().getProperties().get("externalName"));
        } else {
            employee.setSectionId(null);
            employee.setSectionName(null);
        }
        return employee;
    }

    private Employee setPlant(Employee employee, Optional<ODataEntry> jobInfo) {
        Optional<ODataEntry> plant = Optional.ofNullable((ODataEntry) jobInfo.get().getProperties().get("customString15Nav"));
        if (plant.isPresent()) {
            employee.setPlantId((String) plant.get().getProperties().get("externalCode"));
            employee.setPlantName((String) plant.get().getProperties().get("externalName"));
        } else {
            employee.setPlantId(null);
            employee.setPlantName(null);
        }
        return employee;
    }
}
