package com.kilic.yunus.miro.controller;

import com.kilic.yunus.miro.data.dto.CreateWidgetDto;
import com.kilic.yunus.miro.data.dto.FilterDto;
import com.kilic.yunus.miro.data.dto.WidgetDto;
import com.kilic.yunus.miro.data.resource.WidgetResource;
import com.kilic.yunus.miro.service.WidgetService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

/**
 * @author Yunus Kilic
 */
@RestController
@RequestMapping("/widget")
@Validated
public class WidgetController {

    private final WidgetService widgetService;


    public WidgetController(WidgetService widgetService) {
        this.widgetService = widgetService;
    }

    @PostMapping
    public WidgetResource addWidget(@RequestBody @Valid CreateWidgetDto dto) {
        return widgetService.addWidget(dto);
    }

    @PutMapping
    public WidgetResource updateWidget(@RequestBody @Valid WidgetDto dto) {
        return widgetService.updateWidget(dto);
    }

    @DeleteMapping("/{id}")
    public void deleteWidget(@PathVariable("id") Long id) {
        widgetService.deleteWidget(id);
    }

    @GetMapping("/{id}")
    public WidgetResource getById(@PathVariable("id") Long id) {
        return widgetService.getById(id);
    }

    @GetMapping(value = "/page")
    public List<WidgetResource> getAll(FilterDto filterDto, @RequestParam(value = "pageSize", defaultValue = "10") @Min(1) @Max(1000) int pageSize) {
        return widgetService.getPage(filterDto, pageSize);
    }
}
