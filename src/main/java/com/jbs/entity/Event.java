package com.jbs.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import com.google.common.collect.ImmutableMap;
import com.jbs.util.UserSessionUtil;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.joda.time.DateTime;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import javax.persistence.*;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by rizkykojek on 4/15/17.
 */

@NoArgsConstructor
@lombok.Getter
@lombok.Setter
@Entity
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
public class Event {

    @Id
    @GeneratedValue
    @JsonView(DataTablesOutput.View.class)
    private Long id;

    @Transient
    @JsonView(DataTablesOutput.View.class)
    private Integer revisionNumber;

    @Transient
    @JsonView(DataTablesOutput.View.class)
    private Integer counterNumber;

    @ManyToOne
    @JoinColumn(name="employee_id", nullable=false)
    private Employee employee;

    @ManyToOne
    @JoinColumn(name="event_category_id", nullable=false)
    private EventCategory category;

    @ManyToOne
    @JoinColumn(name="event_type_id", nullable=false)
    @JsonView(DataTablesOutput.View.class)
    private EventType eventType;

    @Column(name = "start_date", nullable = false)
    @JsonView(DataTablesOutput.View.class)
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd MMM yyyy")
    private Date startDate;

    @Column(name = "end_date", nullable = false)
    @JsonView(DataTablesOutput.View.class)
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd MMM yyyy")
    private Date endDate;

    @Column(name = "start_time", nullable = false)
    @JsonView(DataTablesOutput.View.class)
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="hh:mm a")
    private Date startTime;

    @ManyToOne
    @JoinColumn(name="event_status_id", nullable = false)
    @JsonView(DataTablesOutput.View.class)
    private EventAdmin eventStatus;

    @ManyToOne
    @JoinColumn(name="eap_id", nullable = false)
    private EventAdmin eap;

    @Column(name = "comment", length = 4000, nullable = false)
    private String comment;

    @Column(name = "eap_comment", length = 4000, nullable = false)
    private String eapComment;

    @Column(name = "is_medical_certificate")
    private Boolean isMedicalCertificate;

    @Column(name = "is_specialist_clearance")
    private Boolean isSpecialistClearance;

    @Column(name = "is_stat_declaration")
    private Boolean isStatDeclaration;

    @Column(name = "is_receipt")
    private Boolean isReceipt;

    @Column(name = "is_funeral_notice")
    private Boolean isFuneralNotice;

    @Column(name = "is_penalty_notice")
    private Boolean isPenaltyNotice;

    @Column(name = "is_other")
    private Boolean isOther;

    @ManyToOne
    @JoinColumn(name="attachment_type_1")
    private EventAdmin attachmentType1;

    @ManyToOne
    @JoinColumn(name="attachment_type_2")
    private EventAdmin attachmentType2;

    @ManyToOne
    @JoinColumn(name="attachment_type_3")
    private EventAdmin attachmentType3;

    @ManyToOne
    @JoinColumn(name="attachment_type_4")
    private EventAdmin attachmentType4;

    @ManyToOne
    @JoinColumn(name="attachment_type_5")
    private EventAdmin attachmentType5;

    @ManyToOne
    @JoinColumn(name="attachment_type_6")
    private EventAdmin attachmentType6;

    @ManyToOne
    @JoinColumn(name="attachment_type_7")
    private EventAdmin attachmentType7;

    @ManyToOne
    @JoinColumn(name="attachment_type_8")
    private EventAdmin attachmentType8;

    @ManyToOne
    @JoinColumn(name="attachment_type_9")
    private EventAdmin attachmentType9;

    @ManyToOne
    @JoinColumn(name="attachment_type_10")
    private EventAdmin attachmentType10;

    @ManyToOne
    @JoinColumn(name="attachment_1")
    private Attachment attachment1;

    @ManyToOne
    @JoinColumn(name="attachment_2")
    private Attachment attachment2;

    @ManyToOne
    @JoinColumn(name="attachment_3")
    private Attachment attachment3;

    @ManyToOne
    @JoinColumn(name="attachment_4")
    private Attachment attachment4;

    @ManyToOne
    @JoinColumn(name="attachment_5")
    private Attachment attachment5;

    @ManyToOne
    @JoinColumn(name="attachment_6")
    private Attachment attachment6;

    @ManyToOne
    @JoinColumn(name="attachment_7")
    private Attachment attachment7;

    @ManyToOne
    @JoinColumn(name="attachment_8")
    private Attachment attachment8;

    @ManyToOne
    @JoinColumn(name="attachment_9")
    private Attachment attachment9;

    @ManyToOne
    @JoinColumn(name="attachment_10")
    private Attachment attachment10;

    @Column(name = "last_update_at")
    @JsonView(DataTablesOutput.View.class)
    private Date lastUpdateAt;

    @ManyToOne
    @JoinColumn(name="last_update_by", nullable=false)
    @JsonView(DataTablesOutput.View.class)
    private Employee lastUpdateBy;

    @PrePersist
    public void prePersist() {
        this.lastUpdateAt = DateTime.now().toDate();
        this.lastUpdateBy = new Employee(UserSessionUtil.getEmployeeId());
    }

    @PreUpdate
    public void preUpdate() {
        this.lastUpdateAt = DateTime.now().toDate();
        this.lastUpdateBy = new Employee(UserSessionUtil.getEmployeeId());
    }

    public Map<EventAdmin, Attachment>  getAllAttachment() {
        Map<EventAdmin, Attachment> attachments = ImmutableMap.<EventAdmin, Attachment> builder()
                .put(attachmentType1, attachment1)
                .put(attachmentType2, attachment2)
                .put(attachmentType3, attachment3)
                .put(attachmentType4, attachment4)
                .put(attachmentType5, attachment5)
                .put(attachmentType6, attachment6)
                .put(attachmentType7, attachment7)
                .put(attachmentType8, attachment8)
                .put(attachmentType9, attachment9)
                .put(attachmentType10, attachment10)
                .build();

        return attachments.entrySet().stream()
                .filter(map -> map.getValue() != null)
                .collect(Collectors.toMap(map -> map.getKey(), map -> map.getValue()));
    }

    public void addAttachment(Attachment attachment, EventAdmin attachmentType) {
        if (attachment1 == null) {
            attachment1 = attachment;
            attachmentType1 = attachmentType;
        } else if (attachment2 == null) {
            attachment2 = attachment;
            attachmentType2 = attachmentType;
        } else if (attachment3 == null) {
            attachment3 = attachment;
            attachmentType3 = attachmentType;
        } else if (attachment4 == null) {
            attachment4 = attachment;
            attachmentType4 = attachmentType;
        } else if (attachment5 == null) {
            attachment5 = attachment;
            attachmentType5 = attachmentType;
        } else if (attachment6 == null) {
            attachment6 = attachment;
            attachmentType6 = attachmentType;
        } else if (attachment7 == null) {
            attachment7 = attachment;
            attachmentType7 = attachmentType;
        } else if (attachment8 == null) {
            attachment8 = attachment;
            attachmentType8 = attachmentType;
        } else if (attachment9 == null) {
            attachment9 = attachment;
            attachmentType9 = attachmentType;
        } else if (attachment10 == null) {
            attachment10 = attachment;
            attachmentType10 = attachmentType;
        }
    }

    public void removeAttachment(long attachmentId) {
        if (attachment1 != null && attachment1.getId() == attachmentId) {
            attachment1 = null;
            attachmentType1 = null;
        } else if (attachment2 != null && attachment2.getId() == attachmentId) {
            attachment2 = null;
            attachmentType2 = null;
        } else if (attachment3 != null && attachment3.getId() == attachmentId) {
            attachment3 = null;
            attachmentType3 = null;
        } else if (attachment4 != null && attachment4.getId() == attachmentId) {
            attachment4 = null;
            attachmentType4 = null;
        } else if (attachment5 != null && attachment5.getId() == attachmentId) {
            attachment5 = null;
            attachmentType5 = null;
        } else if (attachment6 != null && attachment6.getId() == attachmentId) {
            attachment6 = null;
            attachmentType6 = null;
        } else if (attachment7 != null && attachment7.getId() == attachmentId) {
            attachment7 = null;
            attachmentType7 = null;
        } else if (attachment8 != null && attachment8.getId() == attachmentId) {
            attachment8 = null;
            attachmentType8 = null;
        } else if (attachment9 != null && attachment9.getId() == attachmentId) {
            attachment9 = null;
            attachmentType9 = null;
        } else if (attachment10 != null && attachment10.getId() == attachmentId) {
            attachment10 = null;
            attachmentType10 = null;
        }
    }

    public void copyAttachments(Event old) {
        this.attachment1 = old.attachment1;
        this.attachment2 = old.attachment2;
        this.attachment3 = old.attachment3;
        this.attachment4 = old.attachment4;
        this.attachment5 = old.attachment5;
        this.attachment6 = old.attachment6;
        this.attachment7 = old.attachment7;
        this.attachment8 = old.attachment8;
        this.attachment9 = old.attachment9;
        this.attachment10 = old.attachment10;

        this.attachmentType1 = old.attachmentType1;
        this.attachmentType2 = old.attachmentType2;
        this.attachmentType3 = old.attachmentType3;
        this.attachmentType4 = old.attachmentType4;
        this.attachmentType5 = old.attachmentType5;
        this.attachmentType6 = old.attachmentType6;
        this.attachmentType7 = old.attachmentType7;
        this.attachmentType8 = old.attachmentType8;
        this.attachmentType9 = old.attachmentType9;
        this.attachmentType10 = old.attachmentType10;

    }

    public void initializeLazyConnection(){
        Hibernate.initialize(this.getCategory());
        Hibernate.initialize(this.getEmployee());
        Hibernate.initialize(this.getEventStatus());
        Hibernate.initialize(this.getEventType());
        Hibernate.initialize(this.getLastUpdateBy());
        Hibernate.initialize(this.getEap());

        Hibernate.initialize(this.getAttachment1());
        Hibernate.initialize(this.getAttachment2());
        Hibernate.initialize(this.getAttachment3());
        Hibernate.initialize(this.getAttachment4());
        Hibernate.initialize(this.getAttachment5());
        Hibernate.initialize(this.getAttachment6());
        Hibernate.initialize(this.getAttachment7());
        Hibernate.initialize(this.getAttachment8());
        Hibernate.initialize(this.getAttachment9());
        Hibernate.initialize(this.getAttachment10());

        Hibernate.initialize(this.getAttachmentType1());
        Hibernate.initialize(this.getAttachmentType2());
        Hibernate.initialize(this.getAttachmentType3());
        Hibernate.initialize(this.getAttachmentType4());
        Hibernate.initialize(this.getAttachmentType5());
        Hibernate.initialize(this.getAttachmentType6());
        Hibernate.initialize(this.getAttachmentType7());
        Hibernate.initialize(this.getAttachmentType8());
        Hibernate.initialize(this.getAttachmentType9());
        Hibernate.initialize(this.getAttachmentType10());

    }

}
