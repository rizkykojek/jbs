package com.jbs.entity;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

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
    @JsonView(DataTablesOutput.View.class)
    private Long id;

    @Column(length = 2, unique = true)
    @JsonView(DataTablesOutput.View.class)
    private String code;

    @Column(length = 100)
    @JsonView(DataTablesOutput.View.class)
    private String name;

    @Column
    private Boolean status;

    @ManyToOne
    @JoinColumn(name="category_id")
    private PerformanceCategory category;

}
