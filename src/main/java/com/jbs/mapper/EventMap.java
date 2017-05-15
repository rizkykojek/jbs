package com.jbs.mapper;

import com.jbs.dto.EventDto;
import com.jbs.entity.Event;
import org.modelmapper.PropertyMap;

/**
 * Created by rizkykojek on 5/16/17.
 */
public class EventMap extends PropertyMap<EventDto, Event> {

    @Override
    protected void configure() {
        skip().getEventStatus().setStatus(null);
    }
}
