package com.jbs.service.impl;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.jbs.entity.Attachment;
import com.jbs.entity.Performance;
import com.jbs.repository.AttachmentRepository;
import com.jbs.repository.PerformanceRepository;
import com.jbs.service.PerformanceService;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import pl.jsolve.templ4docx.core.Docx;
import pl.jsolve.templ4docx.core.VariablePattern;
import pl.jsolve.templ4docx.variable.TextVariable;
import pl.jsolve.templ4docx.variable.Variables;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by rizkykojek on 5/1/17.
 */
@Service
public class PerformanceServiceImpl implements PerformanceService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private PerformanceRepository performanceRepository;

    @Autowired
    private AttachmentRepository attachmentRepository;

    @Transactional
    public Performance save(Performance performance, List<MultipartFile> files) throws Exception {
        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                Attachment attachment = new Attachment();
                attachment.setDocumentName(file.getOriginalFilename());
                attachment.setContentType(file.getContentType());
                attachment.setFile(file.getBytes());
                String extension = Iterables.getLast(Lists.newArrayList(StringUtils.split(attachment.getDocumentName(), ".")), null);
                attachment.setExtension(extension);
                attachment = attachmentRepository.save(attachment);

                performance.addAttachment(attachment);
            }
        }

        performance = performanceRepository.save(performance);
        return performance;
    }

    @Transactional
    public void removeAttachment(Long performanceId, Long attachmentId) {
        Performance performance = performanceRepository.findOne(performanceId);
        performance.removeAttachment(attachmentId);
        performanceRepository.save(performance);
    }

    @Transactional(readOnly = true)
    public File generateLetterTemplate(Long letterTemplateId) throws Exception {
        DefaultResourceLoader loader = new DefaultResourceLoader();

        Docx docx = new Docx(loader.getResource("classpath:com/jbs/doc/letter.docx").getInputStream());
        docx.setVariablePattern(new VariablePattern("${", "}"));

        // fill template
        Variables variables = preparingVariablesOnLetter();
        docx.fillTemplate(variables);

        // save filled .docx file
        File tempFile = File.createTempFile("tmp", ".docx", new File(loader.getClassLoader().getResource("com/jbs/doc/").getPath()));
        docx.save(new FileOutputStream(tempFile, false));

        return tempFile;
    }

    @Transactional(readOnly = true)
    public List<Performance> getAllPerformanceRevisions(Long performanceId) {
        AuditReader reader = AuditReaderFactory.get(entityManager);
        AuditQuery query = reader.createQuery().forRevisionsOfEntity(Performance.class, true, true);
        query.add(AuditEntity.id().eq(performanceId));
        query.addOrder(AuditEntity.revisionNumber().asc());
        List<Performance> audits = query.getResultList();
        for (Performance performance: audits) {
            performance.getAction().getName(); //by default oneToMany is lazy on auditreader
        }

        return audits;
    }

    private Variables preparingVariablesOnLetter() {
        SimpleDateFormat formatter = new SimpleDateFormat("EEEE, dd MMMM yyyy");
        Variables variables = new Variables();
        variables.addTextVariable(new TextVariable("${employee_id}", "1111222"));
        variables.addTextVariable(new TextVariable("${full_name}", "Derrick Stewart"));
        variables.addTextVariable(new TextVariable("${salutation}", "Mr."));
        variables.addTextVariable(new TextVariable("${title}", "Staff"));
        variables.addTextVariable(new TextVariable("${address1}", "6/10 Smith Street."));
        variables.addTextVariable(new TextVariable("${address2}", "Neutral Bay"));
        variables.addTextVariable(new TextVariable("${state}", "NSW"));
        variables.addTextVariable(new TextVariable("${code}", "12345"));
        variables.addTextVariable(new TextVariable("${date}", formatter.format(new Date())));
        variables.addTextVariable(new TextVariable("${company_name}", "JBS Australia Pty Limited"));
        variables.addTextVariable(new TextVariable("${officer_name}", "John Mckey"));
        variables.addTextVariable(new TextVariable("${officer_title}", "General Manager"));

        return variables;
    }
}
