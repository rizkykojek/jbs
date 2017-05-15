package com.jbs.dto;

import com.google.common.collect.Lists;
import com.jbs.entity.Attachment;
import com.jbs.util.ApplicationUtil;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * Created by rizkykojek on 4/15/17.
 */
@Getter
@Setter
public class EventDto {

    public EventDto(){
        this.attachments = Lists.newArrayList();
        this.files = Lists.newArrayList();
        totalAttachmentsPersisted = 0;
    }

    private Long id;

    private Integer revisionNumber;

    @NotNull
    private Long employeeId;

    @NotNull
    private Long categoryId;

    @NotNull
    private Long eventTypeId;

    @NotNull
    private Long eventStatusId;

    @NotNull
    private Long eapId;

    @NotNull
    @DateTimeFormat(pattern = "dd MMM YYYY")
    private Date startDate;

    @NotNull
    @DateTimeFormat(pattern = "dd MMM YYYY")
    private Date endDate;

    @NotNull
    @DateTimeFormat(pattern = "hh:mm aa")
    private Date startTime;

    @NotEmpty
    private String comment;

    @NotEmpty
    private String eapComment;

    private Boolean isMedicalCertificate;

    private Boolean isSpecialistClearance;

    private Boolean isStatDeclaration;

    private Boolean isReceipt;

    private Boolean isFuneralNotice;

    private Boolean isPenaltyNotice;

    private Boolean isOther;

    private List<MultipartFile> files;

    private List<Attachment> attachments;

    private Integer totalAttachmentsPersisted;

    private Long[] removedAttachments;

    public Boolean isUpdate(){
        return id != null && id != 0;
    }

    public Boolean isAuditVersion(){
        return isUpdate() && revisionNumber != null && revisionNumber != 0;
    }

    @AssertTrue(message = "Maximum size per file should not more than 5 Mb ")
    public boolean isUploadSizeBelowMaximum() {
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

    @AssertTrue(message = "Total attachment file is should not greater than 10 file")
    public boolean isTotalUploadFileBelowMaximum() {
        if (files.stream().filter(f -> !f.isEmpty()).toArray().length - removedAttachments.length + totalAttachmentsPersisted > 10) {
            return false;
        }
        return true;
    }

}
