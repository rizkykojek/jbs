package com.jbs.dto;

import com.jbs.entity.Attachment;
import com.jbs.util.ApplicationUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.ScriptAssert;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.AssertTrue;
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

    public PerformanceDto(Date current) {
        this.startDate = current;
        this.endDate = current;
        this.startTime = current;
    }

    private Long id;

    private Integer revisionNumber;

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
    @DateTimeFormat(pattern = "hh:mm aa")
    private Date startTime;

    private String issuedBy;

    private String place;

    private String attendee;

    private Long supportResponseId;

    private String supportPerson;

    private Long interpreterId;

    private Boolean isSupervisorReport;

    @NotEmpty
    private String comment;

    private List<MultipartFile> files;

    private List<Attachment> attachments;

    private Long[] removedAttachments;

    public Boolean isUpdate(){
        return id != null && id != 0;
    }

    public Boolean isAuditVersion(){
        return isUpdate() && revisionNumber != null && revisionNumber != 0;
    }

    @AssertTrue(message = "Maximum size per file should not more than 5 Mb ")
    public boolean isFileSizeBelow() {
        if (files != null) {
            for (MultipartFile file : files) {
                if (file.getSize() > ApplicationUtil.MAX_UPLOAD_SIZE_PER_FILE) {
                    return false;
                }
            }
        }
        return true;
    }

    @AssertTrue(message = "Start date should not greater than End date")
    public boolean isStartDateBelowEndDate() {
        if (startDate != null && endDate != null) {
            return startDate.before(endDate) || startDate.equals(endDate);
        }
        return true;
    }

}
