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
@Table(name = "letter_template")
public class LetterTemplate {

    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 3)
    private String code;

    @Column(length = 100)
    private String name;

    @Column(name = "template_file")
    private String templateFile;

    @Column
    private Boolean status;

    @ManyToOne
    @JoinColumn(name="action_id")
    private PerformanceAction action;

}
