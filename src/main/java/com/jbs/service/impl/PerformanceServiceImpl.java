package com.jbs.service.impl;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.jbs.entity.Attachment;
import com.jbs.entity.Performance;
import com.jbs.repository.AttachmentRepository;
import com.jbs.repository.PerformanceRepository;
import com.jbs.service.PerformanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by rizkykojek on 5/1/17.
 */
@Service
public class PerformanceServiceImpl implements PerformanceService {

    @Autowired
    private PerformanceRepository performanceRepository;

    @Autowired
    private AttachmentRepository attachmentRepository;

    @Transactional
    public Performance save(Performance performance, List<MultipartFile> files) throws Exception {
        for (MultipartFile file: files) {
            if(!files.isEmpty()) {
                Attachment attachment = new Attachment();
                attachment.setDocumentName(file.getOriginalFilename());
                attachment.setContentType(file.getContentType());
                attachment.setFile(file.getBytes());
                String extension =  Iterables.getLast(Lists.newArrayList(StringUtils.split(attachment.getDocumentName(), ".")), null);
                attachment.setExtension(extension);
                attachment = attachmentRepository.save(attachment);

                performance.addAttachment(attachment);
            }
        }

        performance = performanceRepository.save(performance);
        return performance;
    }

    @Transactional
    public void removeAttachment(Long performanceId, Long attachmentId) {
        Performance performance = performanceRepository.findOne(performanceId);
        performance.removeAttachment(attachmentId);
        performanceRepository.save(performance);
    }
}
