package com.jbs.config.audit;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionEntity;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Created by rizkykojek on 5/1/17.
 */
@Entity(name = "revision_info")
@RevisionEntity(value = RevisionInfoListener.class)
@Getter
@Setter
public class RevisionInfo extends DefaultRevisionEntity {

    @Column(name = "updated_by")
    private Long updatedBy;
}