package com.jbs.entity;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

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
    @JsonView(DataTablesOutput.View.class)
    private Long id;

    @Column(length = 3, unique = true)
    @JsonView(DataTablesOutput.View.class)
    private String code;

    @Column
    @JsonView(DataTablesOutput.View.class)
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
