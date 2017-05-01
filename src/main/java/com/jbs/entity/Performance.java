package com.jbs.entity;

import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * Created by rizkykojek on 4/15/17.
 */

@NoArgsConstructor
@lombok.Getter
@lombok.Setter
@Entity
@Audited
public class Performance {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name="employee_id", nullable=false)
    @NotAudited
    private Employee employee;

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

    @Column(name = "is_interpreter")
    private Boolean isInterpreter;

    @Column(name = "is_supervisor_report")
    private Boolean isSupervisorReport;

    @Column(name = "comment", length = 4000)
    private String comment;

    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(name="performance_attachment", joinColumns=@JoinColumn(name="performance_id"), inverseJoinColumns=@JoinColumn(name="attachment_id"))
    @NotAudited
    public Set<Attachment> attachments;

}
