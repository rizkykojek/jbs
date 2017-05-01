package com.jbs.service;

import com.jbs.entity.Performance;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by efriandika on 5/1/17.
 */
public interface PerformanceService {

    public Performance save(Performance performance, List<MultipartFile> files) throws Exception;

    public void removeAttachment(Long performanceId, Long attachmentId);
}
