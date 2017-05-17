package com.jbs.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.jbs.dto.EventDto;
import com.jbs.entity.Employee;
import com.jbs.entity.Event;
import com.jbs.entity.EventType;
import com.jbs.mapper.EventMap;
import com.jbs.repository.*;
import com.jbs.repository.datatable.EventTableRepository;
import com.jbs.service.EventService;
import com.jbs.util.ApplicationUtil;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.*;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by rizkykojek on 3/21/17.
 */

@Controller
public class EventController {

    @Autowired
    private EventService eventService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EventCategoryRepository eventCategoryRepository;

    @Autowired
    private EventTypeRepository eventTypeRepository;

    @Autowired
    private EventAdminRepository eventAdminRepository;

    @Autowired
    private EventTableRepository eventTableRepository;

    @RequestMapping(value = {"/event/{eventId}", "/event/{eventId}/revision/{revisionNumber}", "/employee/{employeeId}/event"}, method = RequestMethod.GET)
    public String getEvent(@PathVariable Optional<Long> employeeId, @PathVariable Optional<Long> eventId, @PathVariable Optional<Integer> revisionNumber, final Model model) {
        EventDto eventDto = new EventDto();
        if (eventId.isPresent() && revisionNumber.isPresent()) {
            Event event = eventService.findEventRevision(eventId.get(),revisionNumber.get());
            eventDto = convertToDto(event);
            eventDto.setRevisionNumber(revisionNumber.get());
        } else if (eventId.isPresent()) {
            Event event = eventRepository.findOne(eventId.get());
            eventDto = convertToDto(event);
        }

        populateModelAttribute(model, eventDto, employeeId.isPresent() ? employeeId.get() : eventDto.getEmployeeId());
        return "event";
    }

    @RequestMapping(value = "/employee/{employeeId}/event/create", method = RequestMethod.POST)
    public String create(@PathVariable Long employeeId, @Valid EventDto eventDto, BindingResult bindingResult, Model model) throws Exception {
        if (!bindingResult.hasErrors()) {
            Event event = convertToEntity(eventDto, Optional.empty());
            event = eventRepository.save(event);
            eventDto = convertToDto(event);
        }

        populateModelAttribute(model, eventDto, employeeId);
        return "event";
    }

    @RequestMapping(value = "/employee/{employeeId}/event/update/{eventId}", method = RequestMethod.POST)
    public String update(@PathVariable Long employeeId, @PathVariable Optional<Long> eventId, @Valid EventDto eventDto, BindingResult bindingResult, Model model) throws Exception {
        if (!bindingResult.hasErrors()) {
            Event event = convertToEntity(eventDto, eventId);
            event = eventRepository.save(event);
            eventDto = convertToDto(event);
        }

        populateModelAttribute(model, eventDto, employeeId);
        return "event";
    }

    @RequestMapping(value = "/event/types", method = RequestMethod.GET)
    public @ResponseBody List<EventType> getEventTypes(@RequestParam Long categoryId) {
        return eventTypeRepository.findByCategoryIdOrderByName(categoryId);
    }

    @JsonView(DataTablesOutput.View.class)
    @RequestMapping(value = "/event/audit/{eventId}", method = RequestMethod.GET)
    public @ResponseBody DataTablesOutput getEventAudits(@PathVariable Long eventId, @Valid DataTablesInput request) {
        DataTablesOutput<Event> results = new DataTablesOutput<>();
        List<Event> eventRevisions = eventService.findAllEventRevisions(eventId);
        results.setData(eventRevisions);
        return results;
    }

    @JsonView(DataTablesOutput.View.class)
    @RequestMapping(value = "/event/history/{employeeId}", method = RequestMethod.GET)
    public @ResponseBody DataTablesOutput getEventHistory(@PathVariable Long employeeId, @RequestParam Optional<String> startDateFilter, @RequestParam Optional<String> endDateFilter,
                                                                @RequestParam Optional<Integer> monthFilter, @Valid DataTablesInput request) {
        DataTablesOutput<Event> results = eventTableRepository.findAll(request, historySpecification(employeeId, startDateFilter, endDateFilter, monthFilter));
        results.setData(results.getData().stream().sorted((e1, e2) -> Long.compare(e2.getId(), e1.getId())).collect(Collectors.toList()));
        return results;
    }

    public static Specification<Event> historySpecification(Long employeeId, Optional<String> startDate, Optional<String> endDate, Optional<Integer> month) {
        return (Root<Event> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            DateTimeFormatter formatter = DateTimeFormat.forPattern("dd MMM YYYY");
            DateTime currentDate  = DateTime.now().withTimeAtStartOfDay();
            DateTime endFilter;
            DateTime startFilter;

            if(month.isPresent()) {
                endFilter = currentDate;
                startFilter = currentDate.minusMonths(month.get());
            } else {
                endFilter = StringUtils.isNotEmpty(endDate.get()) ? formatter.parseDateTime(endDate.get()) : currentDate.plusMonths(ApplicationUtil.DEFAULT_FILTER_DATE_MONTH);
                startFilter = StringUtils.isNotEmpty(startDate.get()) ? formatter.parseDateTime(startDate.get()) : currentDate.minusMonths(ApplicationUtil.DEFAULT_FILTER_DATE_MONTH);
            }

            /** start or end date filter */
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
            Predicate dateFilter = builder.or(startFilterBetweenDate, endFilterBetweenDate, startDateBetweenFilter, endDateBetweenFilter);

            /** employee filter */
            Join<Event, Employee> employeeJoin = root.join("employee");
            Predicate employeeFilter = builder.equal(employeeJoin.get("id"), employeeId);

            return  builder.and(dateFilter, employeeFilter);
        };
    }

    private void populateModelAttribute(Model model, EventDto eventDto, Long employeeId) {
        Employee employee = employeeRepository.findOne(employeeId);
        model.addAttribute("employee", employee);
        model.addAttribute("eventDto", eventDto);

        model.addAttribute("listCategory", eventCategoryRepository.findAllByOrderByName());
        model.addAttribute("listEventType", eventTypeRepository.findByCategoryIdOrderByName(eventDto.getCategoryId()));
        model.addAttribute("listEventStatus", eventAdminRepository.findByStatusAndTypeOrderBySequenceAsc(Boolean.TRUE, ApplicationUtil.STATUS_TYPE));
        model.addAttribute("listEap", eventAdminRepository.findByStatusAndTypeOrderBySequenceAsc(Boolean.TRUE, ApplicationUtil.EAP_TYPE));
        model.addAttribute("listAttachmentType", eventAdminRepository.findByStatusAndTypeOrderBySequenceAsc(Boolean.TRUE, ApplicationUtil.ATTACHMENT_TYPE));
        model.addAttribute("listEventTypeBasedHistory", eventTypeRepository.findAllByOrderByName());
    }

    private Event convertToEntity(EventDto eventDto, Optional<Long> eventId) {
        modelMapper.addMappings(new EventMap());
        Event event = modelMapper.map(eventDto, Event.class);
        if (eventId.isPresent()) {
            event.setId(eventId.get());
            event.copyAttachments(eventRepository.findOne(eventId.get()));
        }

        return event;
    }

    private EventDto convertToDto(Event event) {
        EventDto eventDto = modelMapper.map(event, EventDto.class);
        return eventDto;
    }

}
