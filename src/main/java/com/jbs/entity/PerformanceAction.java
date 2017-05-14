package com.jbs.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by rizkykojek on 5/1/17.
 */
@NoArgsConstructor
@lombok.Getter
@lombok.Setter
@NamedEntityGraph(name = "PerformanceAction.templates", attributeNodes = @NamedAttributeNode("templates"))
@Entity
@Table(name = "performance_action")
public class PerformanceAction {

    @Id
    @GeneratedValue
    @JsonView(DataTablesOutput.View.class)
    private Long id;

    @Column(length = 3)
    @JsonView(DataTablesOutput.View.class)
    private String code;

    @Column(length = 100)
    @JsonView(DataTablesOutput.View.class)
    private String name;

    @Column
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private Boolean status;

    @JsonIgnore
    @ManyToMany(mappedBy = "actions")
    private Set<PerformanceCategory> categories;

    @JsonIgnore
    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(name="performance_action_template", joinColumns=@JoinColumn(name="action_id"), inverseJoinColumns=@JoinColumn(name="template_id"))
    public Set<LetterTemplate> templates;

}
