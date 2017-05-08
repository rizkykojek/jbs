package com.jbs.service;

import com.jbs.entity.Attachment;
import com.jbs.entity.Performance;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

/**
 * Created by efriandika on 5/1/17.
 */
public interface PerformanceService {

    Performance save(Performance performance, List<MultipartFile> files) throws Exception;

    void removeAttachment(Long performanceId, Long attachmentId);

    File generateLetterTemplate(Long letterTemplateId) throws Exception;

    List<Performance> getAllPerformanceRevisions(Long performanceId);

    List<Attachment> getAllAttachment(Long performanceId);
}
