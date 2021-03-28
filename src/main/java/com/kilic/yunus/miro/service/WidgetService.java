package com.kilic.yunus.miro.service;

import com.kilic.yunus.miro.data.dto.CreateWidgetDto;
import com.kilic.yunus.miro.data.dto.FilterDto;
import com.kilic.yunus.miro.data.dto.WidgetDto;
import com.kilic.yunus.miro.data.mapper.WidgetMapper;
import com.kilic.yunus.miro.data.model.Widget;
import com.kilic.yunus.miro.data.repository.WidgetRepository;
import com.kilic.yunus.miro.data.resource.WidgetResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Yunus Kilic
 */
@Service
public class WidgetService {

    private final WidgetMapper widgetMapper;

    private final WidgetRepository widgetRepository;

    public WidgetService(WidgetMapper widgetMapper, WidgetRepository widgetRepository) {
        this.widgetMapper = widgetMapper;
        this.widgetRepository = widgetRepository;
    }

    public synchronized WidgetResource addWidget(CreateWidgetDto dto) {
        Widget widget = widgetMapper.toModelFromCreate(dto);
        return widgetMapper.toResource(widgetRepository.addWidget(widget));
    }


    public synchronized WidgetResource updateWidget(WidgetDto dto) {
        Widget widget = widgetMapper.toModel(dto);
        return widgetMapper.toResource(widgetRepository.updateWidget(widget));
    }

    public synchronized void deleteWidget(Long id) {
        boolean success = widgetRepository.deleteWidget(id);
        if (!success) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "");
        }
    }

    public synchronized WidgetResource getById(Long id) {
        Optional<Widget> optionalWidget = widgetRepository.getById(id);
        if (optionalWidget.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "");
        }
        return widgetMapper.toResource(optionalWidget.get());
    }

    public synchronized List<WidgetResource> getPage(FilterDto dto, int pageSize) {
        List<Widget> widgets = widgetRepository.getPage(dto, pageSize);
        return widgets.stream().map(widgetMapper::toResource).collect(Collectors.toList());
    }
}
