package com.jbs.repository;

import com.jbs.entity.PerformanceAdmin;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by rizkykojek on 3/10/17.
 */
public interface PerformanceAdminRepository extends CrudRepository<PerformanceAdmin, Long> {

    List<PerformanceAdmin> findByStatusAndType(Boolean status, String type);

}
