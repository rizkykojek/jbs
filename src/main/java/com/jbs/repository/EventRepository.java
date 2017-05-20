package com.jbs.repository;

import com.jbs.entity.Event;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;

/**
 * Created by rizkykojek on 4/15/17.
 */
public interface EventRepository extends CrudRepository<Event, Long> {

    Long countByEmployeeIdAndEventTypeCodeAndEndDateGreaterThanEqual(Long employeeId, String eventTypeCode, Date filterDate);

}
