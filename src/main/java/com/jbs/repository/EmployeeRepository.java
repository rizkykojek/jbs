package com.jbs.repository;

import com.jbs.entity.Employee;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by rizkykojek on 3/10/17.
 */
public interface EmployeeRepository extends CrudRepository<Employee, Long> {

    public List<Employee> findByLastName(String lastName);
}
