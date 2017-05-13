package com.jbs.repository;

import com.jbs.entity.PerformanceAction;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by rizkykojek on 3/10/17.
 */
public interface PerformanceActionRepository extends CrudRepository<PerformanceAction, Long> {

    @EntityGraph(value = "PerformanceAction.templates", type = EntityGraph.EntityGraphType.LOAD)
    PerformanceAction findById(Long id);

}
