package com.jbs.controller;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.jsolve.templ4docx.core.Docx;
import pl.jsolve.templ4docx.core.VariablePattern;
import pl.jsolve.templ4docx.variable.TextVariable;
import pl.jsolve.templ4docx.variable.Variables;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by rizkykojek on 3/5/17.
 */
@Controller
public class PerformanceController {

    @RequestMapping(value = "/performance/generate_letter", method = RequestMethod.GET)
    public void generateLetter(HttpServletResponse response) {
        try {
            DefaultResourceLoader loader = new DefaultResourceLoader();

            Docx docx = new Docx(loader.getResource("classpath:com/jbs/doc/letter.docx").getInputStream());
            docx.setVariablePattern(new VariablePattern("${", "}"));

            // fill template
            Variables variables = preparingVariables();
            docx.fillTemplate(variables);

            // save filled .docx file
            File tempFile = File.createTempFile("tmp",".docx", new File (loader.getClassLoader().getResource("com/jbs/doc/").getPath()));
            docx.save(new FileOutputStream(tempFile, false));

            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=letter.docx");
            response.setContentLength((int)tempFile.length());
            InputStream inputStream = new BufferedInputStream(new FileInputStream(tempFile));

            FileCopyUtils.copy(inputStream, response.getOutputStream());
        } catch (IOException ex) {
            throw new RuntimeException("IOError writing file to output stream");
        }
    }

    private Variables preparingVariables() {
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
