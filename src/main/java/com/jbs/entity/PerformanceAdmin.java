package com.jbs.entity;

import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Created by rizkykojek on 5/1/17.
 */
@NoArgsConstructor
@lombok.Getter
@lombok.Setter
@Entity
@Table(name = "performance_admin")
public class PerformanceAdmin {

    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 30, unique = true)
    private String code;

    @Column(length = 50)
    private String type;

    @Column(length = 100)
    private String value;

    @Column
    private Integer sequence;

    @Column
    private Boolean status;

}
