package com.jbs.entity;

import lombok.NoArgsConstructor;

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
    private String employeeNumber;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @ManyToOne
    @JoinColumn(name="position_id", nullable=false)
    private Position position;

    @ManyToOne
    @JoinColumn(name="department_id", nullable=false)
    private Department department;

    public String getFullName(){
        return firstName + " " + lastName;
    }

}
