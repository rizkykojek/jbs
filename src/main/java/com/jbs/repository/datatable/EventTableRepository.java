package com.jbs.repository.datatable;

import com.jbs.entity.Event;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;

/**
 * Created by rizkykojek on 5/14/17.
 */
public interface EventTableRepository extends DataTablesRepository<Event, Long> {
}
