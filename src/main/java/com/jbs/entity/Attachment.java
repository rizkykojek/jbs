package com.jbs.entity;

import com.jbs.util.OpenCmisUtil;
import com.sap.ecm.api.EcmService;
import lombok.NoArgsConstructor;
import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.commons.PropertyIds;
import org.apache.chemistry.opencmis.commons.data.ContentStream;
import org.apache.chemistry.opencmis.commons.enums.VersioningState;
import org.springframework.beans.factory.annotation.Autowired;

import javax.naming.NamingException;
import javax.persistence.*;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.util.HashMap;
import java.util.Map;

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

    @Transient
    private byte[] file;

    @PostPersist
    public void prePersist() throws NamingException {
        Session session = OpenCmisUtil.openSession(OpenCmisUtil.ecmService());
        Folder root = session.getRootFolder();

        Map<String, Object> properties = new HashMap<>();
        properties.put(PropertyIds.OBJECT_TYPE_ID, "cmis:document");
        properties.put(PropertyIds.NAME, this.id + "." + this.extension);

        /** save file to HANA Document Storage */
        byte[] content = this.getFile();
        InputStream stream = new ByteArrayInputStream(content);
        ContentStream contentStream = session.getObjectFactory().createContentStream(this.getDocumentName(), content.length, this.getContentType(), stream);
        Document document = root.createDocument(properties, contentStream, VersioningState.NONE);

        /** get documentId from HANA Document Storage */
        this.setDocumentId(document.getId());
    }
}
