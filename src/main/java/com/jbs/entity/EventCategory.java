package com.jbs.entity;

import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;

/**
 * Created by rizkykojek on 5/1/17.
 */
@NoArgsConstructor
@lombok.Getter
@lombok.Setter
@Entity
@Table(name = "event_category")
public class EventCategory {

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
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private Boolean status;
}
