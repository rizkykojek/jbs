package com.jbs.repository;

import com.jbs.entity.Event;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by rizkykojek on 4/15/17.
 */
public interface EventRepository extends CrudRepository<Event, Long> {

    Long countByEmployeeIdAndEventTypeCode(Long employeeId, String eventTypeCode);

}
