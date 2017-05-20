package com.jbs.service;

import com.jbs.entity.Event;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by efriandika on 5/15/17.
 */
public interface EventService {

    Event save(Event event, List<MultipartFile> files, List<Long> attachmentTypeIds, Long[] removedAttachmentIds) throws Exception;

    List<Event> findAllEventRevisions(Long eventId);

    Event findEventRevision(Long eventId, Integer revisionNumber);
}
