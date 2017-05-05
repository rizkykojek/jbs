package com.jbs.entity;

import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import javax.persistence.*;
import java.util.Date;

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
    private Long id;

    @ManyToOne
    @JoinColumn(name="employee_id", nullable=false)
    private Employee employee;

    @ManyToOne
    @JoinColumn(name="performance_category_id", nullable=false)
    private PerformanceCategory category;

    @ManyToOne
    @JoinColumn(name="performance_action_id", nullable=false)
    private PerformanceAction action;

    @ManyToOne
    @JoinColumn(name="letter_template_id")
    private LetterTemplate letterTemplate;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Temporal(TemporalType.TIME)
    @Column(name = "start_time")
    private Date startTime;

    @Column(name = "issued_by")
    private String issuedBy;

    @Column(name = "place")
    private String place;

    @Column(name = "attendee")
    private String attendee;

    @Column(name = "support_response")
    private String supportResponse;

    @Column(name = "support_person")
    private String supportPerson;

    @Column(name = "interpreter")
    private String interpreter;

    @Column(name = "is_supervisor_report")
    private Boolean isSupervisorReport;

    @Column(name = "comment", length = 4000)
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
        }
    }

}
