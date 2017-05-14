package com.jbs.repository;

import com.jbs.entity.EventAdmin;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by rizkykojek on 3/10/17.
 */
public interface EventAdminRepository extends CrudRepository<EventAdmin, Long> {

    List<EventAdmin> findByStatusAndTypeOrderBySequenceAsc(Boolean status, String type);

}
