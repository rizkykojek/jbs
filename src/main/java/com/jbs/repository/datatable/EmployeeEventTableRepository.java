package com.jbs.repository.datatable;

import com.jbs.entity.EmployeeEvent;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by rizkykojek on 3/10/17.
 */
public interface EmployeeEventTableRepository extends DataTablesRepository<EmployeeEvent, Long> {


}
