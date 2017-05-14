package com.jbs.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import com.google.common.collect.Lists;
import com.jbs.util.UserSessionUtil;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.joda.time.DateTime;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by rizkykojek on 4/15/17.
 */

@NoArgsConstructor
@lombok.Getter
@lombok.Setter
@Entity
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
public class Performance {

    @Id
    @GeneratedValue
    @JsonView(DataTablesOutput.View.class)
    private Long id;

    @Transient
    private Integer revisionNumber;

    @ManyToOne
    @JoinColumn(name="employee_id", nullable=false)
    private Employee employee;

    @ManyToOne
    @JoinColumn(name="performance_category_id", nullable=false)
    private PerformanceCategory category;

    @ManyToOne
    @JoinColumn(name="performance_action_id", nullable=false)
    @JsonView(DataTablesOutput.View.class)
    private PerformanceAction action;

    @ManyToOne
    @JoinColumn(name="letter_template_id")
    private LetterTemplate letterTemplate;

    @Column(name = "start_date", nullable = false)
    @JsonView(DataTablesOutput.View.class)
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd MMM yyyy", timezone="Australia/Sydney")
    private Date startDate;

    @Column(name = "end_date", nullable = false)
    @JsonView(DataTablesOutput.View.class)
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd MMM yyyy", timezone="Australia/Sydney")
    private Date endDate;

    @Column(name = "start_time", nullable = false)
    @JsonView(DataTablesOutput.View.class)
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="hh:mm a", timezone="Australia/Sydney")
    private Date startTime;

    @Column(name = "issued_by")
    private String issuedBy;

    @Column(name = "place")
    private String place;

    @Column(name = "attendee")
    private String attendee;

    @Column(name = "support_person")
    private String supportPerson;

    @ManyToOne
    @JoinColumn(name="interpreter_id")
    private PerformanceAdmin interpreter;

    @ManyToOne
    @JoinColumn(name="support_response_id")
    private PerformanceAdmin supportResponse;

    @Column(name = "is_supervisor_report")
    private Boolean isSupervisorReport;

    @Column(name = "comment", length = 4000, nullable = false)
    private String comment;

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
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd MMM yyyy hh:mm a", timezone="Australia/Sydney")
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

    public List<Attachment> getAllAttachment() {
        List<Attachment> attachments = Lists.newArrayList(attachment1, attachment2, attachment3, attachment4, attachment5,
                attachment6, attachment7, attachment8, attachment9, attachment10);
        return attachments.stream()
                .filter(attachment -> attachment != null)
                .collect(Collectors.toList());
    }

    public void addAttachment(Attachment attachment) {
        if (attachment1 == null) {
            attachment1 = attachment;
        } else if (attachment2 == null) {
            attachment2 = attachment;
        } else if (attachment3 == null) {
            attachment3 = attachment;
        } else if (attachment4 == null) {
            attachment4 = attachment;
        } else if (attachment5 == null) {
            attachment5 = attachment;
        } else if (attachment6 == null) {
            attachment6 = attachment;
        } else if (attachment7 == null) {
            attachment7 = attachment;
        } else if (attachment8 == null) {
            attachment8 = attachment;
        } else if (attachment9 == null) {
            attachment9 = attachment;
        } else if (attachment10 == null) {
            attachment10 = attachment;
        }
    }

    public void removeAttachment(long attachmentId) {
        if (attachment1 != null && attachment1.getId() == attachmentId) {
            attachment1 = null;
        } else if (attachment2 != null && attachment2.getId() == attachmentId) {
            attachment2 = null;
        } else if (attachment3 != null && attachment3.getId() == attachmentId) {
            attachment3 = null;
        } else if (attachment4 != null && attachment4.getId() == attachmentId) {
            attachment4 = null;
        } else if (attachment5 != null && attachment5.getId() == attachmentId) {
            attachment5 = null;
        } else if (attachment6 != null && attachment6.getId() == attachmentId) {
            attachment6 = null;
        } else if (attachment7 != null && attachment7.getId() == attachmentId) {
            attachment7 = null;
        } else if (attachment8 != null && attachment8.getId() == attachmentId) {
            attachment8 = null;
        } else if (attachment9 != null && attachment9.getId() == attachmentId) {
            attachment9 = null;
        } else if (attachment10 != null && attachment10.getId() == attachmentId) {
            attachment10 = null;
        }
    }

    public void copyAttachments(Performance old) {
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
    }

    public void initializeLazyConnection(){
        Hibernate.initialize(this.getAction());
        Hibernate.initialize(this.getCategory());
        Hibernate.initialize(this.getEmployee());
        Hibernate.initialize(this.getLetterTemplate());
        Hibernate.initialize(this.getInterpreter());
        Hibernate.initialize(this.getSupportResponse());
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
        Hibernate.initialize(this.getLastUpdateBy());
    }

}
