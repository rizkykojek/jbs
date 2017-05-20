package com.jbs.dto;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jbs.entity.Attachment;
import com.jbs.entity.EventAdmin;
import com.jbs.util.ApplicationUtil;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by rizkykojek on 4/15/17.
 */
@Getter
@Setter
public class EventDto {

    public EventDto(){
        this.attachments = Maps.newHashMap();
        this.files = Lists.newArrayList();
        totalAttachmentsPersisted = 0;
        eapComment = "Not Applicable";
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

    private List<Long> attachmentTypeIds;

    private List<MultipartFile> files;

    private Map<EventAdmin, Attachment> attachments;

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

    @AssertTrue(message = "End date should greater than Start date")
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

    @AssertTrue(message = "Attachment only support for this extension: .jpg .jpeg .gif .png .bmp .pdf .doc .docx .tiff")
    public boolean isValidAttachmentFileType() {
        if (files != null) {
            for (MultipartFile file : files) {
                if (file.getSize() > 0) {
                    String extension = file.getOriginalFilename().split("\\.")[1];
                    boolean matched = Arrays.stream(ApplicationUtil.EXTENSION_TYPE_ACCEPTED).anyMatch(extension::equals);
                    if (matched == false) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

}
