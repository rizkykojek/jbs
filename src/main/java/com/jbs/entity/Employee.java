package com.jbs.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by rizkykojek on 3/10/17.
 */

@NoArgsConstructor
@lombok.Getter
@lombok.Setter
@Entity
public class Employee {

    @Id
    @GeneratedValue
    @JsonView(DataTablesOutput.View.class)
    private Long id;

    @Transient
    @JsonView(DataTablesOutput.View.class)
    private Integer counterNumber;

    @Column(name = "person_id_external", unique = true, nullable = false)
    @JsonView(DataTablesOutput.View.class)
    private String personIdExternal;

    @Column(name = "user_id", unique = true, nullable = false)
    @JsonView(DataTablesOutput.View.class)
    private String userId;

    @Column(name = "first_name", nullable = false)
    @JsonView(DataTablesOutput.View.class)
    private String firstName;

    @Column(name = "middle_name")
    @JsonView(DataTablesOutput.View.class)
    private String middleName;

    @Column(name = "last_name")
    @JsonView(DataTablesOutput.View.class)
    private String lastName;

    @Column(name = "full_name")
    @JsonView(DataTablesOutput.View.class)
    private String fullName;

    @Column(name = "gender")
    private String gender;

    @Column(name = "salutation")
    private String salutation;

    @Column(name = "date_of_birth")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd MMM yyyy")
    private Date dateOfBirth;

    @Column(name = "position_id")
    @JsonView(DataTablesOutput.View.class)
    private String positionId;

    @Column(name = "position_name")
    @JsonView(DataTablesOutput.View.class)
    private String positionName;

    @Column(name = "department_id")
    @JsonView(DataTablesOutput.View.class)
    private String departmentId;

    @Column(name = "department_name")
    @JsonView(DataTablesOutput.View.class)
    private String departmentName;

    @Column(name = "site_id")
    @JsonView(DataTablesOutput.View.class)
    private String siteId;

    @Column(name = "site_name")
    @JsonView(DataTablesOutput.View.class)
    private String siteName;

    @Column(name = "section_id")
    @JsonView(DataTablesOutput.View.class)
    private String sectionId;

    @Column(name = "section_name")
    @JsonView(DataTablesOutput.View.class)
    private String sectionName;

    @Column(name = "shift_id")
    @JsonView(DataTablesOutput.View.class)
    private String shiftId;

    @Column(name = "shift_name")
    @JsonView(DataTablesOutput.View.class)
    private String shiftName;

    @Column(name = "location_id")
    @JsonView(DataTablesOutput.View.class)
    private String locationId;

    @Column(name = "location_name")
    @JsonView(DataTablesOutput.View.class)
    private String locationName;

    @Column(name = "processed_at", nullable = false)
    private Date processedAt;

    @PrePersist
    public void prePersist() {
        this.fullName = builderFullName();
        this.processedAt = DateTime.now().toDate();
    }

    @PreUpdate
    public void preUpdate() {
        this.fullName = builderFullName();
        this.processedAt = DateTime.now().toDate();
    }

    public Employee(Long id) {
        this.id = id;
    }

    private String builderFullName(){
        StringBuilder builder = new StringBuilder();
        builder.append(firstName);
        if (middleName != null) {
            builder.append(" ");
            builder.append(middleName);
        }
        if (lastName != null) {
            builder.append(" ");
            builder.append(lastName);
        }
        return builder.toString();
    }

}
