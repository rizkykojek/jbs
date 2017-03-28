package com.jbs.entity;

import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Created by rizkykojek on 3/28/17.
 */
@NoArgsConstructor
@lombok.Getter
@lombok.Setter
@Entity
@Table(name = "employee_event")
public class EmployeeEvent {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name="employee_id", nullable=false)
    private Employee employee;

    @Column(name = "event_name")
    private String eventName;

    @Column(name = "event_type")
    private Integer eventType;
}
