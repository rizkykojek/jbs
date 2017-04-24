package com.jbs.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * Created by rizkykojek on 4/15/17.
 */
@Getter
@Setter
@NoArgsConstructor
public class PerformanceDto {

    private Long id;

    private Long employeeId;

    @NotNull
    @DateTimeFormat(pattern = "dd MMM YYYY")
    private Date startDate;

    @NotNull
    @DateTimeFormat(pattern = "dd MMM YYYY")
    private Date endDate;

    @NotNull
    @DateTimeFormat(pattern = "HH:mm a")
    private Date time;

    @NotEmpty
    private String issuedBy;

    @NotEmpty
    private String place;

    @NotEmpty
    private String attendee;

    @NotEmpty
    private String supportResponse;

    @NotEmpty
    private String supportPerson;

    private Boolean isInterpreter;

    private Boolean isSupervisorReport;

    @NotEmpty
    private String comment;

    private List<MultipartFile> files;

}
