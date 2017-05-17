package com.jbs.service.impl;

import com.jbs.config.audit.RevisionInfo;
import com.jbs.entity.Event;
import com.jbs.service.EventService;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        return audits;
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

}
