package com.jbs.dto;

import com.jbs.entity.Attachment;
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

    @NotNull
    private Long employeeId;

    @NotNull
    private Long parentCategoryId;

    @NotNull
    private Long categoryId;

    @NotNull
    private Long actionId;

    private Long letterTemplateId;

    @NotNull
    @DateTimeFormat(pattern = "dd MMM YYYY")
    private Date startDate;

    @NotNull
    @DateTimeFormat(pattern = "dd MMM YYYY")
    private Date endDate;

    @NotNull
    @DateTimeFormat(pattern = "HH:mm a")
    private Date startTime;

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

    @NotEmpty
    private String interpreter;

    private Boolean isSupervisorReport;

    @NotEmpty
    private String comment;

    private List<MultipartFile> files;

    private List<Attachment> attachments;

    public Boolean isUpdate(){
        return id != null && id != 0;
    }

}
