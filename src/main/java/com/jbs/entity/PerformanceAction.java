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
@Table(name = "performance_action")
public class PerformanceAction {

    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 2, unique = true)
    private String code;

    @Column(length = 100)
    private String name;

    @Column
    private Boolean status;

    @ManyToOne
    @JoinColumn(name="category_id")
    private PerformanceCategory category;

}
