package com.jbs.service;

import com.jbs.entity.Event;

import java.util.List;

/**
 * Created by efriandika on 5/15/17.
 */
public interface EventService {

    List<Event> findAllEventRevisions(Long eventId);

    Event findEventRevision(Long eventId, Integer revisionNumber);
}
