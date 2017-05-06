package com.jbs.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.jbs.dto.PerformanceDto;
import com.jbs.entity.*;
import com.jbs.repository.*;
import com.jbs.repository.datatable.PerformanceTableRepository;
import com.jbs.service.PerformanceService;
import com.jbs.util.ApplicationUtil;
import org.joda.time.DateTime;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
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

    @Autowired
    private PerformanceCategoryRepository performanceCategoryRepository;

    @Autowired
    private PerformanceActionRepository performanceActionRepository;

    @Autowired
    private LetterTemplateRepository letterTemplateRepository;

    @Autowired
    private PerformanceTableRepository performanceTableRepository;

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

        populateModelAttribute(model, performanceDto);

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

        populateModelAttribute(model, performanceDto);

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

        populateModelAttribute(model, performanceDto);

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

    @RequestMapping(value = "/performance/categories", method = RequestMethod.GET)
    public @ResponseBody List<PerformanceCategory> getCategories(@RequestParam Long parentCategoryId) {
        return performanceCategoryRepository.findByParentCategoryNotNullAndParentCategoryId(parentCategoryId);
    }

    @RequestMapping(value = "/performance/actions", method = RequestMethod.GET)
    public @ResponseBody List<PerformanceAction> getActions(@RequestParam Long parentCategoryId) {
        return performanceActionRepository.findByCategoryId(parentCategoryId);
    }

    @RequestMapping(value = "/performance/letter_templates", method = RequestMethod.GET)
    public @ResponseBody List<LetterTemplate> getLetterTemplates(@RequestParam Long actionId) {
        return letterTemplateRepository.findByActionId(actionId);
    }

    @JsonView(DataTablesOutput.View.class)
    @RequestMapping(value = "/performance/history", method = RequestMethod.GET)
    public @ResponseBody DataTablesOutput getPerformanceHistory(@Valid DataTablesInput request) {
        DataTablesOutput<Performance> results = performanceTableRepository.findAll(request, historySpecification(request));
        return results;
    }

    public static Specification<Performance> historySpecification(DataTablesInput request) {
        return (root, query, builder) -> {
            DateTime endFilter = DateTime.now().withTimeAtStartOfDay();
            DateTime startFilter = endFilter.minusMonths(ApplicationUtil.DEFAULT_FILTER_DATE_MONTH);
            Predicate startFilterBetweenDate = builder.and(
                    builder.lessThanOrEqualTo(root.get("startDate"), startFilter.toDate()),
                    builder.greaterThanOrEqualTo(root.get("endDate"), startFilter.toDate())
            );
            Predicate endFilterBetweenDate = builder.and(
                    builder.lessThanOrEqualTo(root.get("startDate"), endFilter.toDate()),
                    builder.greaterThanOrEqualTo(root.get("endDate"), endFilter.toDate())
            );
            Predicate startDateBetweenFilter = builder.between(root.get("startDate"), startFilter.toDate(), endFilter.toDate());
            Predicate endDateBetweenFilter = builder.between(root.get("endDate"), startFilter.toDate(), endFilter.toDate());

            return  builder.or(startFilterBetweenDate, endFilterBetweenDate, startDateBetweenFilter, endDateBetweenFilter);
        };
    }

    private void populateModelAttribute(Model model, PerformanceDto performanceDto) {
        model.addAttribute("listCategory", performanceCategoryRepository.findByParentCategoryIsNull());
        model.addAttribute("listSubCategory", performanceCategoryRepository.findByParentCategoryNotNullAndParentCategoryId(performanceDto.getParentCategoryId()));
        model.addAttribute("listAction", performanceActionRepository.findByCategoryId(performanceDto.getParentCategoryId()));
        model.addAttribute("listLetterTemplate", letterTemplateRepository.findByActionId(performanceDto.getActionId()));

        /** should revisit, based on performance history */
        model.addAttribute("listActionBasedHistory", performanceActionRepository.findAll());
    }

    private Performance convertToEntity(PerformanceDto performanceDto, Optional<Long> performanceId) {
        Performance performance = modelMapper.map(performanceDto, Performance.class);;
        if (performanceId.isPresent()) {
            performance.setId(performanceId.get());
        }

        return performance;
    }

    private PerformanceDto convertToDto(Performance performance) {
        PerformanceDto performanceDto = modelMapper.map(performance, PerformanceDto.class);
        performanceDto.setParentCategoryId(performance.getCategory().getParentCategory().getId());
        return performanceDto;
    }

}
