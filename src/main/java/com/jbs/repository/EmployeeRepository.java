package com.jbs.repository;

import com.jbs.entity.Employee;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by rizkykojek on 3/10/17.
 */
public interface EmployeeRepository extends CrudRepository<Employee, Long> {

    Employee findByPersonIdExternal(String personIdExternal);

}
