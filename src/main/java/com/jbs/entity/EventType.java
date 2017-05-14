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
@Table(name = "event_type")
public class EventType {

    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 3, unique = true)
    private String code;

    @Column
    private String name;

    @Column
    private Integer sequence;

    @Column
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private Boolean status;

    @ManyToOne
    @JoinColumn(name="category_id", nullable=false)
    private EventCategory category;
}
