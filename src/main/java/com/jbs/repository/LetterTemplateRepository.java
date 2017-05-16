package com.jbs.repository;

import com.jbs.entity.LetterTemplate;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by rizkykojek on 3/10/17.
 */
public interface LetterTemplateRepository extends CrudRepository<LetterTemplate, Long> {

    List<LetterTemplate> findAllByOrderByName();

}
