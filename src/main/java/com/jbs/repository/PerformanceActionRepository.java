package com.jbs.repository;

import com.jbs.entity.PerformanceAction;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by rizkykojek on 3/10/17.
 */
public interface PerformanceActionRepository extends CrudRepository<PerformanceAction, Long> {

    List<PerformanceAction> findByCategoryId(Long parentCategoryId);

}
