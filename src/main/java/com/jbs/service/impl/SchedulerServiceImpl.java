package com.jbs.service.impl;

import com.jbs.entity.Employee;
import com.jbs.repository.EmployeeRepository;
import com.jbs.service.SchedulerService;
import com.jbs.util.ODataUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.olingo.odata2.api.edm.Edm;
import org.apache.olingo.odata2.api.ep.entry.ODataEntry;
import org.apache.olingo.odata2.api.ep.feed.ODataDeltaFeed;
import org.apache.olingo.odata2.api.ep.feed.ODataFeed;
import org.joda.time.DateTime;
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

    @Scheduled(fixedDelay = 1000000000, initialDelay = 1)
    @Transactional
    public void populateEmployeeSfOnStart() throws Exception {
        this.populateEmployeeSF();
    }

    @Scheduled(cron = "0 0 1 * * *") // 1:00 AM every day.
    @Transactional
    public void populateEmployeeSfOnSchedule() throws Exception {
        this.populateEmployeeSF();
    }

    @Transactional
    public void populateEmployeeSF() throws Exception {
        Calendar current = Calendar.getInstance();
        current.setTime(DateTime.now().withTimeAtStartOfDay().toDate());
        ODataFeed perPerson = ODataUtil.readFeed(edm, "PerPerson","$expand=employmentNav,personalInfoNav/salutationNav,homeAddressNavDEFLT/cityNav,homeAddressNavDEFLT/stateNav,phoneNav/phoneTypeNav");
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
            Optional<ODataEntry> employment = employmentNav.get().getEntries().stream()
                    .filter(e -> isCurrentBetween(current,(Calendar) e.getProperties().get("startDate"),(Calendar) e.getProperties().get("endDate")))
                    .sorted((e1,e2) -> ((Calendar) e2.getProperties().get("endDate")).compareTo(((Calendar) e1.getProperties().get("startDate"))))
                    .findFirst();
            if (employment.isPresent()) {
                String userId = (String) employment.get().getProperties().get("userId");
                employee.setUserId(userId);

                Optional<Calendar> hireDate = Optional.ofNullable((Calendar) employment.get().getProperties().get("startDate"));
                employee.setHireDate(hireDate.isPresent() ? hireDate.get().getTime() : null);

                ODataFeed empJob = ODataUtil.readFeed(edm, "EmpJob","$expand=payGradeNav,jobCodeNav,departmentNav,positionNav,locationNav,shiftCodeNav,customString5Nav,customString15Nav&$filter=userId%20eq%20%27"+ userId +"%27");
                Optional<ODataEntry> jobInfo = empJob.getEntries().stream()
                        .filter(e -> isCurrentBetween(current,(Calendar) e.getProperties().get("startDate"),(Calendar) e.getProperties().get("endDate")))
                        .sorted((e1,e2) -> ((Calendar) e2.getProperties().get("endDate")).compareTo(((Calendar) e1.getProperties().get("startDate"))))
                        .findFirst();
                if (jobInfo.isPresent()) {
                    employee = setDepartment(employee, jobInfo);
                    employee = setPosition(employee, jobInfo);
                    employee = setShift(employee, jobInfo);
                    employee = setLocation(employee, jobInfo);
                    employee = setSection(employee, jobInfo);
                    employee = setSite(employee, jobInfo);
                    employee = setJobClass(employee, jobInfo);
                    employee = setPayGrade(employee, jobInfo);
                }
            }

            Optional<ODataDeltaFeed> personalInfoNav = Optional.ofNullable((ODataDeltaFeed) person.getProperties().get("personalInfoNav"));
            Optional<ODataEntry> personalInfo = personalInfoNav.get().getEntries().stream()
                    .filter(e -> isCurrentBetween(current,(Calendar) e.getProperties().get("startDate"),(Calendar) e.getProperties().get("endDate")))
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

                Optional<ODataEntry> salutationNav = Optional.ofNullable((ODataEntry) personalInfo.get().getProperties().get("salutationNav"));
                if (salutationNav.isPresent()) {
                    employee.setSalutation((String) salutationNav.get().getProperties().get("externalCode"));
                } else {
                    employee.setSalutation(null);
                }
            }

            Optional<ODataDeltaFeed> addressNav = Optional.ofNullable((ODataDeltaFeed) person.getProperties().get("homeAddressNavDEFLT"));
            Optional<ODataEntry> address = addressNav.get().getEntries().stream()
                    .filter(e -> isCurrentBetween(current,(Calendar) e.getProperties().get("startDate"),(Calendar) e.getProperties().get("endDate")))
                    .sorted((e1,e2) -> ((Calendar) e2.getProperties().get("endDate")).compareTo(((Calendar) e1.getProperties().get("startDate"))))
                    .findFirst();
            if (address.isPresent()) {
                Optional<String> zipCode = Optional.ofNullable((String) address.get().getProperties().get("zipCode"));
                employee.setAddressZipcode(zipCode.isPresent() ? zipCode.get() : null);

                Optional<String> address1 = Optional.ofNullable((String) address.get().getProperties().get("address1"));
                employee.setAddress1(address1.isPresent() ? address1.get() : null);

                Optional<ODataEntry> stateNav = Optional.ofNullable((ODataEntry) address.get().getProperties().get("stateNav"));
                if (stateNav.isPresent()) {
                    employee.setAddressState((String) stateNav.get().getProperties().get("externalCode"));
                } else {
                    employee.setAddressState(null);
                }

                Optional<ODataEntry> cityNav = Optional.ofNullable((ODataEntry) address.get().getProperties().get("cityNav"));
                if (cityNav.isPresent()) {
                    employee.setAddressCity((String) cityNav.get().getProperties().get("externalCode"));
                } else {
                    employee.setAddressCity(null);
                }
            }

            Optional<ODataDeltaFeed> phoneNav = Optional.ofNullable((ODataDeltaFeed) person.getProperties().get("phoneNav"));
            for(ODataEntry phone: phoneNav.get().getEntries()) {
                ODataEntry phoneType = (ODataEntry) phone.getProperties().get("phoneTypeNav");
                if (StringUtils.equals((String) phoneType.getProperties().get("externalCode"),"O")) {
                    employee.setMobilePhone((String) phone.getProperties().get("phoneNumber"));
                } else if (StringUtils.equals((String) phoneType.getProperties().get("externalCode"),"H")) {
                    employee.setHomePhone((String) phone.getProperties().get("phoneNumber"));
                }
            }

            if (employee.getPersonIdExternal() == null || employee.getUserId() == null || employee.getFirstName() == null) {
                continue;
            }
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
            employee.setPositionName((String) position.get().getProperties().get("externalName_en_GB"));
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

    private Employee setSite(Employee employee, Optional<ODataEntry> jobInfo) {
        Optional<ODataEntry> site = Optional.ofNullable((ODataEntry) jobInfo.get().getProperties().get("customString15Nav"));
        if (site.isPresent()) {
            employee.setSiteId((String) site.get().getProperties().get("externalCode"));
            employee.setSiteName((String) site.get().getProperties().get("externalName"));
        } else {
            employee.setSiteId(null);
            employee.setSiteName(null);
        }
        return employee;
    }

    private Employee setJobClass(Employee employee, Optional<ODataEntry> jobInfo) {
        Optional<ODataEntry> jobCode = Optional.ofNullable((ODataEntry) jobInfo.get().getProperties().get("jobCodeNav"));
        if (jobCode.isPresent()) {
            employee.setJobClassId((String) jobCode.get().getProperties().get("externalCode"));
            employee.setJobClassName((String) jobCode.get().getProperties().get("name"));
        } else {
            employee.setJobClassId(null);
            employee.setJobClassName(null);
        }
        return employee;
    }

    private Employee setPayGrade(Employee employee, Optional<ODataEntry> jobInfo) {
        Optional<ODataEntry> payGrade = Optional.ofNullable((ODataEntry) jobInfo.get().getProperties().get("payGradeNav"));
        if (payGrade.isPresent()) {
            employee.setPayGradeId((String) payGrade.get().getProperties().get("externalCode"));
            employee.setPayGradeName((String) payGrade.get().getProperties().get("name"));
        } else {
            employee.setPayGradeId(null);
            employee.setPayGradeName(null);
        }
        return employee;
    }

    private Boolean isCurrentBetween(Calendar current, Calendar start, Calendar end) {
        boolean isAfterStart;
        if (start == null) {
            isAfterStart = false;
        } else {
            isAfterStart = (start.before(current) || start.equals(current));
        }

        boolean isBeforeEnd = true;
        if (end != null) {
            isBeforeEnd = (end.after(current) || end.equals(current));
        }

        return isAfterStart && isBeforeEnd;
    }
}
