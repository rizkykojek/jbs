package com.jbs.repository;

import com.jbs.entity.EventCategory;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by rizkykojek on 5/14/17.
 */
public interface EventCategoryRepository extends CrudRepository<EventCategory, Long> {

    List<EventCategory> findAllByOrderByName();
}
