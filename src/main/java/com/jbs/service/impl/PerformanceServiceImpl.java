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
import com.jbs.util.UserSessionUtil;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
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
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

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
    public List<Performance> findAllPerformanceRevisions(Long performanceId, Boolean idOrderAsc) {
        AuditReader reader = AuditReaderFactory.get(entityManager);
        AuditQuery query = reader.createQuery().forRevisionsOfEntity(Performance.class, false, true);
        query.add(AuditEntity.id().eq(performanceId));
        query.addOrder(AuditEntity.revisionNumber().asc());
        List<Performance> audits = new ArrayList<>();
        List<Object[]> objects = query.getResultList();
        AtomicInteger atomicInteger = new AtomicInteger(1);
        for (Object[] object: objects) {
            Performance performance = (Performance) object[0];
            performance.setRevisionNumber(((RevisionInfo) object[1]).getId());
            performance.setCounterNumber(atomicInteger.getAndIncrement());
            performance.initializeLazyConnection();
            audits.add(performance);
        }

        return idOrderAsc ? audits : Lists.reverse(audits);
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
        docx.setVariablePattern(new VariablePattern("<", ">"));

        // fill template
        Variables variables = preparingVariablesOnLetter(employee);
        docx.fillTemplate(variables);

        // save filled .docx file
        File tempFile = File.createTempFile("tmp", ".docx", new File(loader.getClassLoader().getResource("com/jbs/doc/").getPath()));
        docx.save(new FileOutputStream(tempFile, false));

        return tempFile;
    }

    private Variables preparingVariablesOnLetter(Employee employee) {
        DateTimeFormatter weekday = DateTimeFormat.forPattern("EEEE");
        DateTimeFormatter day = DateTimeFormat.forPattern("dd");
        DateTimeFormatter month = DateTimeFormat.forPattern("MMMM");
        DateTimeFormatter year = DateTimeFormat.forPattern("yyyy");
        DateTime dt = DateTime.now();

        Variables variables = new Variables();
        variables.addTextVariable(new TextVariable("<GIVEN_NAME>", employee.getFirstName()));
        variables.addTextVariable(new TextVariable("<MIDDLE_NAME>", "<MIDDLE_NAME>"));
        variables.addTextVariable(new TextVariable("<SURNAME>", employee.getLastName()));
        variables.addTextVariable(new TextVariable("<ADDRESS_STREET>", "<ADDRESS_STREET>"));
        variables.addTextVariable(new TextVariable("<ADDRESS_SUBURB>", "<ADDRESS_SUBURB>"));
        variables.addTextVariable(new TextVariable("<WEEKDAY>", weekday.print(dt)));
        variables.addTextVariable(new TextVariable("<DAY>", day.print(dt)));
        variables.addTextVariable(new TextVariable("<MONTH>", month.print(dt)));
        variables.addTextVariable(new TextVariable("<YEAR>", year.print(dt)));
        variables.addTextVariable(new TextVariable("<DATE>", "<DATE>"));
        variables.addTextVariable(new TextVariable("<SALUTATION>", "<SALUTATION>"));
        variables.addTextVariable(new TextVariable("<OFFICER_NAME>", UserSessionUtil.getFullName()));
        variables.addTextVariable(new TextVariable("<OFFICER_TITLE>", "<OFFICER_TITLE>"));
        variables.addTextVariable(new TextVariable("<HRID>", "<HRID>"));
        variables.addTextVariable(new TextVariable("<SITE>", "<SITE>"));
        variables.addTextVariable(new TextVariable("<SITE_ADDRESS>", "<SITE_ADDRESS>"));
        variables.addTextVariable(new TextVariable("<PHONE>", "<PHONE>"));
        variables.addTextVariable(new TextVariable("<JOB_CLASS>", "<JOB_CLASS>"));
        variables.addTextVariable(new TextVariable("<START_DATE>", "<START_DATE>"));
        variables.addTextVariable(new TextVariable("<EMP_ADDRESS>", "<EMP_ADDRESS>"));
        variables.addTextVariable(new TextVariable("<SAPID>", "<SAPID>"));
        variables.addTextVariable(new TextVariable("<SECTION>", "<SECTION>"));
        variables.addTextVariable(new TextVariable("<EMP_PHONE>", "<EMP_PHONE>"));
        variables.addTextVariable(new TextVariable("<ADDRESS_LINE_1>", "<ADDRESS_LINE_1>"));
        variables.addTextVariable(new TextVariable("<ADDRESS_LINE_2>", "<ADDRESS_LINE_2>"));
        variables.addTextVariable(new TextVariable("<empID>", employee.getEmployeeNumber()));

        return variables;
    }
}
