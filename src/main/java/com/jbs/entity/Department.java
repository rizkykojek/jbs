package com.jbs.entity;

import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by rizkykojek on 3/21/17.
 */

@NoArgsConstructor
@lombok.Getter
@lombok.Setter
@Entity
public class Department {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true, name = "code")
    private String code;

    @Column(name = "name")
    private String name;
}
