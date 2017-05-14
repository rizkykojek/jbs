package com.jbs.service.impl;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.jbs.config.audit.RevisionInfo;
import com.jbs.entity.Attachment;
import com.jbs.entity.Employee;
import com.jbs.entity.LetterTemplate;
import com.jbs.entity.Performance;
import com.jbs.repository.AttachmentRepository;
import com.jbs.repository.EmployeeRepository;
import com.jbs.repository.LetterTemplateRepository;
import com.jbs.repository.PerformanceRepository;
import com.jbs.service.PerformanceService;
import com.jbs.util.ApplicationUtil;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
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
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

    @Autowired
    private LetterTemplateRepository letterTemplateRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Transactional
    public Performance save(Performance performance, List<MultipartFile> files, Long[] removedAttachmentIds) throws Exception {
        for (Long attachmentId : removedAttachmentIds){
            performance.removeAttachment(attachmentId);
        }

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

    /*@Transactional(readOnly = true)
    public File generateLetterTemplate(Long letterTemplateId, Long employeeId) throws Exception {
        DefaultResourceLoader loader = new DefaultResourceLoader();
        File tempFile = filledTemplatefile(loader.getResource("classpath:com/jbs/doc/letter.docx").getInputStream(), employeeRepository.findOne(employeeId));
        return tempFile;
    }*/

    @Transactional(readOnly = true)
    public File generateLetterTemplate(Long letterTemplateId, Long employeeId) throws Exception {
        File file = null;
        JSch jsch = new JSch();
        Session session = null;
        ChannelSftp sftpChannel = null;

        try {
            LetterTemplate letterTemplate = letterTemplateRepository.findOne(letterTemplateId);
            Employee employee = employeeRepository.findOne(employeeId);

            session = jsch.getSession(ApplicationUtil.FTP_SERVER_USER, ApplicationUtil.FTP_SERVER_URL, ApplicationUtil.FTP_SERVER_PORT);
            session.setConfig("StrictHostKeyChecking", "no");
            session.setPassword(ApplicationUtil.FTP_SERVER_PASSWORD);
            session.connect();

            Channel channel = session.openChannel("sftp");
            channel.connect();
            sftpChannel = (ChannelSftp) channel;
            String remotePath = ApplicationUtil.FTP_SERVER_FOLDER + employee.getSite().getId() + "/" + letterTemplate.getTemplateFile();
            InputStream inputStream = sftpChannel.get(remotePath);

            file = filledTemplatefile(inputStream, employee);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            session.disconnect();
            sftpChannel.disconnect();
        }

        return file;
    }

    @Transactional(readOnly = true)
    public List<Performance> findAllPerformanceRevisions(Long performanceId) {
        AuditReader reader = AuditReaderFactory.get(entityManager);
        AuditQuery query = reader.createQuery().forRevisionsOfEntity(Performance.class, false, true);
        query.add(AuditEntity.id().eq(performanceId));
        query.addOrder(AuditEntity.revisionNumber().asc());
        List<Performance> audits = new ArrayList<>();
        List<Object[]> objects = query.getResultList();
        for (Object[] object: objects) {
            Performance performance = (Performance) object[0];
            performance.setRevisionNumber(((RevisionInfo) object[1]).getId());
            performance.initializeLazyConnection();
            audits.add(performance);
        }

        return Lists.reverse(audits);
    }

    @Transactional(readOnly = true)
    public Performance findPerformanceRevision(Long performanceId, Integer revisionNumber) {
        AuditReader reader = AuditReaderFactory.get(entityManager);
        AuditQuery query = reader.createQuery().forEntitiesAtRevision(Performance.class, revisionNumber);
        query.add(AuditEntity.id().eq(performanceId));
        Performance performance = (Performance) query.getSingleResult();
        performance.initializeLazyConnection();
        return performance;
    }

    private File filledTemplatefile(InputStream is, Employee employee) throws Exception {
        DefaultResourceLoader loader = new DefaultResourceLoader();
        Docx docx = new Docx(is);
        docx.setVariablePattern(new VariablePattern("${", "}"));

        // fill template
        Variables variables = preparingVariablesOnLetter(employee);
        docx.fillTemplate(variables);

        // save filled .docx file
        File tempFile = File.createTempFile("tmp", ".docx", new File(loader.getClassLoader().getResource("com/jbs/doc/").getPath()));
        docx.save(new FileOutputStream(tempFile, false));

        return tempFile;
    }

    private Variables preparingVariablesOnLetter(Employee employee) {
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
