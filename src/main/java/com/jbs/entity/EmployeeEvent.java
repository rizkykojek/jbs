package com.jbs.entity;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

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
    @JsonView(DataTablesOutput.View.class)
    private Long id;

    @ManyToOne
    @JoinColumn(name="employee_id", nullable=false)
    @JsonView(DataTablesOutput.View.class)
    private Employee employee;

    @Column(name = "event_name")
    @JsonView(DataTablesOutput.View.class)
    private String eventName;

    @Column(name = "event_type")
    private Integer eventType;

    @JsonView(DataTablesOutput.View.class)
    private Integer counterNumber;
}
