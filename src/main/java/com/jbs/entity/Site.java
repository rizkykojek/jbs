package com.jbs.entity;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by rizkykojek on 5/14/17.
 */
@NoArgsConstructor
@lombok.Getter
@lombok.Setter
@Entity
public class Site {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "code", unique = true, nullable = false)
    private String code;

    @Column(name = "name", nullable = false)
    @JsonView(DataTablesOutput.View.class)
    private String name;

    @Column
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private Boolean status;

}
