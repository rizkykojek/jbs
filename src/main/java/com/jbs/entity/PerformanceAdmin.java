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
@Table(name = "performance_admin")
public class PerformanceAdmin {

    @Id
    @GeneratedValue
    @JsonView(DataTablesOutput.View.class)
    private Long id;

    @Column(length = 30, unique = true)
    private String code;

    @Column(length = 50)
    private String type;

    @Column(length = 100)
    @JsonView(DataTablesOutput.View.class)
    private String value;

    @Column
    private Integer sequence;

    @Column
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private Boolean status;

}
