package com.jbs.repository;

import com.jbs.entity.EmployeeEvent;
import com.jbs.entity.Shift;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by rizkykojek on 3/10/17.
 */
public interface EmployeeEventRepository extends CrudRepository<EmployeeEvent, Long> {

    List<EmployeeEvent> findByEventType(Integer eventType);

}
