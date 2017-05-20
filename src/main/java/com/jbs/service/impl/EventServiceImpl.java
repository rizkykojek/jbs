package com.jbs.service.impl;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.jbs.config.audit.RevisionInfo;
import com.jbs.entity.Attachment;
import com.jbs.entity.Event;
import com.jbs.entity.EventAdmin;
import com.jbs.repository.AttachmentRepository;
import com.jbs.repository.EventAdminRepository;
import com.jbs.repository.EventRepository;
import com.jbs.service.EventService;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by rizkykojek on 5/15/17.
 */
@Service
public class EventServiceImpl implements EventService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private AttachmentRepository attachmentRepository;

    @Autowired
    private EventAdminRepository eventAdminRepository;

    @Transactional
    public Event save(Event event, List<MultipartFile> files, List<Long> attachmentTypeIds, Long[] removedAttachmentIds) throws Exception {
        for (Long attachmentId : removedAttachmentIds){
            event.removeAttachment(attachmentId);
        }

        for (int i=0; i < files.size(); i++) {
            MultipartFile file = files.get(i);
            if (!file.isEmpty()) {
                Attachment attachment = new Attachment();
                attachment.setDocumentName(file.getOriginalFilename());
                attachment.setContentType(file.getContentType());
                attachment.setFile(file.getBytes());
                String extension = Iterables.getLast(Lists.newArrayList(StringUtils.split(attachment.getDocumentName(), ".")), null);
                attachment.setExtension(extension);
                attachment = attachmentRepository.save(attachment);

                EventAdmin attachmentType = eventAdminRepository.findOne(attachmentTypeIds.get(i));
                event.addAttachment(attachment, attachmentType);
            }
        }

        event = eventRepository.save(event);
        return event;
    }

    @Transactional(readOnly = true)
    public List<Event> findAllEventRevisions(Long eventId) {
        AuditReader reader = AuditReaderFactory.get(entityManager);
        AuditQuery query = reader.createQuery().forRevisionsOfEntity(Event.class, false, true);
        query.add(AuditEntity.id().eq(eventId));
        query.addOrder(AuditEntity.revisionNumber().asc());
        List<Event> audits = new ArrayList<>();
        List<Object[]> objects = query.getResultList();
        AtomicInteger atomicInteger = new AtomicInteger(1);
        for (Object[] object: objects) {
            Event event = (Event) object[0];
            event.setRevisionNumber(((RevisionInfo) object[1]).getId());
            event.setCounterNumber(atomicInteger.getAndIncrement());
            event.initializeLazyConnection();
            audits.add(event);
        }

        return Lists.reverse(audits);
    }

    @Transactional(readOnly = true)
    public Event findEventRevision(Long eventId, Integer revisionNumber) {
        AuditReader reader = AuditReaderFactory.get(entityManager);
        AuditQuery query = reader.createQuery().forEntitiesAtRevision(Event.class, revisionNumber);
        query.add(AuditEntity.id().eq(eventId));
        Event event = (Event) query.getSingleResult();
        event.initializeLazyConnection();
        return event;
    }

    @Transactional(readOnly = true)
    public String getEventSummary(Long employeeId){
        StringBuilder sb = new StringBuilder();
        sb.append("UA(" + eventRepository.countByEmployeeIdAndEventTypeCode(employeeId, "UA") + ")");
        sb.append(" | ");
        sb.append("AB(" + eventRepository.countByEmployeeIdAndEventTypeCode(employeeId, "AB") + ")");
        sb.append(" | ");
        sb.append("PC(" + eventRepository.countByEmployeeIdAndEventTypeCode(employeeId, "PC") + ")");
        sb.append(" | ");
        sb.append("PCN(" + eventRepository.countByEmployeeIdAndEventTypeCode(employeeId, "PCN") + ")");
        return sb.toString();
    }

}
