package com.jbs.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * Created by rizkykojek on 4/15/17.
 */
@Getter
@Setter
@NoArgsConstructor
public class PerformanceDto {

    private Long id;

    private Long employeeId;

    private Date startDate;

    private Date endDate;

    private Date time;

    private String issuedBy;

    private String place;

    private String attendee;

    private String supportResponse;

    private String supportPerson;

    private Boolean isInterpreter;

    private Boolean isSupervisorReport;

    private String comment;

}
