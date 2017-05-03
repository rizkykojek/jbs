package com.jbs.controller;

import com.jbs.dto.PerformanceDto;
import com.jbs.entity.Employee;
import com.jbs.entity.Performance;
import com.jbs.repository.EmployeeRepository;
import com.jbs.repository.PerformanceRepository;
import com.jbs.service.PerformanceService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Optional;

/**
 * Created by rizkykojek on 3/5/17.
 */
@Controller
public class PerformanceController {

    @Autowired
    private PerformanceRepository performanceRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PerformanceService performanceService;

    @RequestMapping(value = {"/performance/{performanceId}", "/employee/{employeeId}/performance"}, method = RequestMethod.GET)
    public String getPerformance(@PathVariable Optional<Long> employeeId, @PathVariable Optional<Long> performanceId, final Model model) {
        Employee employee = null;
        PerformanceDto performanceDto = null;

        if (performanceId.isPresent()) {
            Performance performance = performanceRepository.findOne(performanceId.get());
            employee = performance.getEmployee();
            performanceDto = convertToDto(performance);

        } else if (employeeId.isPresent()) {
            employee = employeeRepository.findOne(employeeId.get());
            performanceDto = new PerformanceDto();
        }

        model.addAttribute(performanceDto);
        model.addAttribute(employee);

        return "performance";
    }

    @RequestMapping(value = "/employee/{employeeId}/performance/create", method = RequestMethod.POST)
    public String create(@PathVariable Long employeeId, @Valid PerformanceDto performanceDto, BindingResult bindingResult, Model model) throws Exception {
        Employee employee = employeeRepository.findOne(employeeId);
        model.addAttribute(employee);

        if (!bindingResult.hasErrors()) {
            Performance performance = convertToEntity(performanceDto, Optional.empty());
            performance = performanceService.save(performance, performanceDto.getFiles());
            model.addAttribute(convertToDto(performance));
        }
        return "performance";
    }

    @RequestMapping(value = "/employee/{employeeId}/performance/update/{performanceId}", method = RequestMethod.POST)
    public String update(@PathVariable Long employeeId, @PathVariable Optional<Long> performanceId, @Valid PerformanceDto performanceDto, BindingResult bindingResult, Model model) throws Exception {
        Employee employee = employeeRepository.findOne(employeeId);
        model.addAttribute(employee);

        if (!bindingResult.hasErrors()) {
            Performance performance = convertToEntity(performanceDto, performanceId);
            performance = performanceService.save(performance, performanceDto.getFiles());
            model.addAttribute(convertToDto(performance));
        }

        return "performance";
    }

    @RequestMapping(value = "/performance/generate_letter", method = RequestMethod.GET)
    public void generateLetter(HttpServletResponse response) throws Exception {
        File tempFile = performanceService.generateLetterTemplate(null);

        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=letter.docx");
        response.setContentLength((int) tempFile.length());
        InputStream inputStream = new BufferedInputStream(new FileInputStream(tempFile));

        FileCopyUtils.copy(inputStream, response.getOutputStream());
    }

    private Performance convertToEntity(PerformanceDto performanceDto, Optional<Long> performanceId) {
        Performance performance = null;
        if (performanceId.isPresent()) {
            performance = performanceRepository.findOne(performanceId.get());
            modelMapper.map(performanceDto, performance);
            performance.setId(performanceId.get());
        } else {
            performance = modelMapper.map(performanceDto, Performance.class);
        }

        return performance;
    }

    private PerformanceDto convertToDto(Performance performance) {
        PerformanceDto performanceDto = modelMapper.map(performance, PerformanceDto.class);
        return performanceDto;
    }

}
