package com.jbs.entity;

import com.fasterxml.jackson.annotation.JsonView;
import com.google.gson.annotations.SerializedName;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import javax.persistence.*;

/**
 * Created by rizkykojek on 3/10/17.
 */

@NoArgsConstructor
@lombok.Getter
@lombok.Setter
@Entity
public class Employee {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "employee_number", unique = true, nullable = false)
    @JsonView(DataTablesOutput.View.class)
    @SerializedName("personIdExternal")
    private String employeeNumber;

    @Column(name = "first_name", nullable = false)
    @JsonView(DataTablesOutput.View.class)
    private String firstName;

    @Column(name = "last_name")
    @JsonView(DataTablesOutput.View.class)
    private String lastName;

    @Column(name = "full_name")
    @JsonView(DataTablesOutput.View.class)
    private String fullName;

    @ManyToOne
    @JoinColumn(name="position_id", nullable=false)
    @JsonView(DataTablesOutput.View.class)
    private Position position;

    @ManyToOne
    @JoinColumn(name="department_id", nullable=false)
    @JsonView(DataTablesOutput.View.class)
    private Department department;

    @ManyToOne
    @JoinColumn(name="section_id", nullable=false)
    @JsonView(DataTablesOutput.View.class)
    private Section section;

    @ManyToOne
    @JoinColumn(name="shift_id", nullable=false)
    @JsonView(DataTablesOutput.View.class)
    private Shift shift;

    @PrePersist
    public void prePersist() {
        this.fullName = this.firstName + " " + this.lastName;
    }

    @PreUpdate
    public void preUpdate() {
        this.fullName = this.firstName + " " + this.lastName;
    }

}
