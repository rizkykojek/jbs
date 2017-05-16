package com.jbs.repository;

import com.jbs.entity.EventType;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by rizkykojek on 5/14/17.
 */
public interface EventTypeRepository extends CrudRepository<EventType, Long> {

    List<EventType> findByCategoryIdOrderByName(Long categoryId);

    List<EventType> findAllByOrderByName();

}
