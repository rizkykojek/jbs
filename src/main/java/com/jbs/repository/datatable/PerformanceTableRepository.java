package com.jbs.repository.datatable;

import com.jbs.entity.Performance;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;

/**
 * Created by rizkykojek on 5/6/17.
 */
public interface PerformanceTableRepository extends DataTablesRepository<Performance, Long> {

}
