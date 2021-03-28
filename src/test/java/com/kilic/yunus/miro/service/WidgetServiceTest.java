package com.kilic.yunus.miro.service;

import com.kilic.yunus.miro.data.dto.CreateWidgetDto;
import com.kilic.yunus.miro.data.dto.FilterDto;
import com.kilic.yunus.miro.data.dto.WidgetDto;
import com.kilic.yunus.miro.data.mapper.WidgetMapper;
import com.kilic.yunus.miro.data.model.Widget;
import com.kilic.yunus.miro.data.repository.WidgetRepository;
import com.kilic.yunus.miro.data.resource.WidgetResource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class WidgetServiceTest {

    @Autowired
    WidgetService widgetService;
    @Autowired
    WidgetRepository widgetRepository;
    @Autowired
    WidgetMapper widgetMapper;

    CreateWidgetDto createWidgetDto;
    CreateWidgetDto createWidgetDtoWithoutZIndex;
    CreateWidgetDto createWidgetDtoZIndex5;
    WidgetDto widgetDto;
    CreateWidgetDto filterFirstDto;
    CreateWidgetDto filterSecondDto;
    CreateWidgetDto filterThirdDto;
    FilterDto filterDto;

    @BeforeEach
    public void setup() {
        createWidgetDto = new CreateWidgetDto(50, 50, 1, 100, 100);
        widgetDto = new WidgetDto(1L, 100, 50, 1, 100, 100, LocalDateTime.now());
        createWidgetDtoWithoutZIndex = new CreateWidgetDto(50, 50, null, 100, 100);
        createWidgetDtoZIndex5 = new CreateWidgetDto(50, 50, 5, 100, 100);

        filterFirstDto = new CreateWidgetDto(50, 50, 1, 100, 100);
        filterSecondDto = new CreateWidgetDto(50, 100, 1, 100, 100);
        filterThirdDto = new CreateWidgetDto(100, 100, 1, 100, 100);

        filterDto = new FilterDto(0, 0, 100, 150);
    }

    @AfterEach
    public void clear() {
        widgetRepository.clearAll();
    }

    @Test
    void addWidget() {
        WidgetResource widgetResource = widgetService.addWidget(createWidgetDto);
        assertTrue(widgetResource.getId() > 0);
        assertEquals(createWidgetDto.getZindex(), widgetResource.getZindex());
    }

    @Test
    void addWidget_withoutZIndex() {
        WidgetResource widgetResource = widgetService.addWidget(createWidgetDtoWithoutZIndex);
        assertTrue(widgetResource.getId() > 0);
        assertEquals(0, widgetResource.getZindex());
    }

    @Test
    void addWidget_addToSameZIndex() {
        widgetService.addWidget(createWidgetDto);
        widgetService.addWidget(createWidgetDto);
        widgetService.addWidget(createWidgetDto);

        List<Widget> widgets = widgetRepository.getAll();
        assertEquals(3, widgets.size());
        assertEquals(1, widgets.get(0).getZindex());
        assertEquals(2, widgets.get(1).getZindex());
        assertEquals(3, widgets.get(2).getZindex());
    }

    @Test
    void addWidget_addToSameZIndexShiftOne() {
        widgetService.addWidget(createWidgetDto);
        widgetService.addWidget(createWidgetDtoZIndex5);
        widgetService.addWidget(createWidgetDto);


        List<Widget> widgets = widgetRepository.getAll();
        assertEquals(3, widgets.size());
        assertEquals(1, widgets.get(0).getZindex());
        assertEquals(2, widgets.get(1).getZindex());
        assertEquals(5, widgets.get(2).getZindex());
    }

    @Test
    void getById() {
        widgetService.addWidget(createWidgetDto);
        WidgetResource widgetResource = widgetService.getById(1L);
        assertNotNull(widgetResource);
        assertEquals(1L, widgetResource.getId());
    }

    @Test
    void getById_NotExisting() {
        assertThrows(ResponseStatusException.class, () -> {
            widgetService.getById(1L);
        });
    }

    @Test
    void delete() {
        widgetService.addWidget(createWidgetDto);
        assertEquals(1, widgetRepository.getAll().size());
        widgetService.deleteWidget(1L);
        assertTrue(CollectionUtils.isEmpty(widgetRepository.getAll()));
    }

    @Test
    void delete_NotExisting() {
        assertThrows(ResponseStatusException.class, () -> {
            widgetService.deleteWidget(1L);
        });
    }

    @Test
    void update() {
        widgetService.addWidget(createWidgetDto);
        assertEquals(1, widgetRepository.getAll().size());
        assertEquals(createWidgetDto.getX(), widgetRepository.getAll().get(0).getX());
        widgetService.updateWidget(widgetDto);
        assertEquals(widgetDto.getX(), widgetRepository.getAll().get(0).getX());
    }

    @Test
    void update_NotExisting() {
        assertThrows(ResponseStatusException.class, () -> {
            widgetService.updateWidget(widgetDto);
        });
    }

    @Test
    void getPage_SizeSmallerThanTotal() {
        widgetService.addWidget(createWidgetDto);
        widgetService.addWidget(createWidgetDto);
        widgetService.addWidget(createWidgetDto);
        widgetService.addWidget(createWidgetDto);
        widgetService.addWidget(createWidgetDto);
        widgetService.addWidget(createWidgetDto);

        List<WidgetResource> page = widgetService.getPage(filterDto, 3);
        assertEquals(6, widgetRepository.getAll().size());
        assertEquals(3, page.size());
    }

    @Test
    void getPage_SizeBiggerThanTotal() {
        widgetService.addWidget(createWidgetDto);
        widgetService.addWidget(createWidgetDto);
        widgetService.addWidget(createWidgetDto);
        widgetService.addWidget(createWidgetDto);
        widgetService.addWidget(createWidgetDto);
        widgetService.addWidget(createWidgetDto);

        List<WidgetResource> page = widgetService.getPage(filterDto, 10);
        assertEquals(6, widgetRepository.getAll().size());
        assertEquals(6, page.size());
    }

}