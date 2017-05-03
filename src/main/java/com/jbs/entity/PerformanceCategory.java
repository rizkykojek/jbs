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
@Table(name = "performance_category")
public class PerformanceCategory {

    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 10, unique = true)
    private String code;

    @Column
    private String name;

    @Column
    private Integer sequence;

    @Column
    private Boolean status;

    @ManyToOne
    @JoinColumn(name="parent_category_id")
    private PerformanceCategory parentCategory;

}
