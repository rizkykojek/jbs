package com.jbs.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by rizkykojek on 5/1/17.
 */
@NoArgsConstructor
@lombok.Getter
@lombok.Setter
@NamedEntityGraph(name = "PerformanceCategory.actions", attributeNodes = @NamedAttributeNode("actions"))
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

    @JsonIgnore
    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(name="performance_category_action", joinColumns=@JoinColumn(name="category_id"), inverseJoinColumns=@JoinColumn(name="action_id"))
    public Set<PerformanceAction> actions;

}
