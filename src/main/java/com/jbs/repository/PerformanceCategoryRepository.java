package com.jbs.repository;

import com.jbs.entity.PerformanceCategory;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by rizkykojek on 3/10/17.
 */
public interface PerformanceCategoryRepository extends CrudRepository<PerformanceCategory, Long> {

    List<PerformanceCategory> findByParentCategoryIsNullOrderByName();

    List<PerformanceCategory> findByParentCategoryNotNullAndParentCategoryIdOrderByName(Long parentCategoryId);

    @EntityGraph(value = "PerformanceCategory.actions", type = EntityGraph.EntityGraphType.LOAD)
    PerformanceCategory findById(Long id);

}
