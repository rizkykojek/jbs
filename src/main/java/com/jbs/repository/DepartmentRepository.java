package com.jbs.repository;

import com.jbs.entity.Department;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by rizkykojek on 3/10/17.
 */
public interface DepartmentRepository extends CrudRepository<Department, Long> {

    Department findOneByCode(String code);
}
