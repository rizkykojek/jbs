package com.jbs.service;

import com.jbs.entity.Performance;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

/**
 * Created by rizkykojek on 5/1/17.
 */
public interface PerformanceService {

    Performance save(Performance performance, List<MultipartFile> files, Long[] removedAttachmentIds) throws Exception;

    File generateLetterTemplate(Long letterTemplateId, Long employeeId) throws Exception;

    List<Performance> findAllPerformanceRevisions(Long performanceId);

    Performance findPerformanceRevision(Long performanceId, Integer revisionNumber);
}
