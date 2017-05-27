package com.jbs.mapper;

import com.jbs.dto.PerformanceDto;
import com.jbs.entity.Performance;
import org.modelmapper.PropertyMap;

/**
 * Created by rizkykojek on 5/16/17.
 */
public class PerformanceSkipDestinationMap extends PropertyMap<PerformanceDto, Performance> {

    @Override
    protected void configure() {
        skip().getPerformanceStatus().setStatus(null);
    }
}
