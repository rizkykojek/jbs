package com.jbs.entity;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Blob;

/**
 * Created by rizkykojek on 4/23/17.
 */

@NoArgsConstructor
@lombok.Getter
@lombok.Setter
@Entity
public class Attachment {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "document_id", unique = true)
    private String documentId;

    @Column(name = "document_name", nullable = false)
    private String documentName;

    @Column(name = "extension", nullable = false)
    private String extension;

    @Column(name="content_type", nullable = false)
    private String contentType;

    @Lob
    @Column(name = "file", nullable = false)
    private byte[] file;
}
