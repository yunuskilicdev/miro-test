package com.kilic.yunus.miro.data.repository;

import com.kilic.yunus.miro.data.dto.FilterDto;
import com.kilic.yunus.miro.data.model.Widget;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * @author Yunus Kilic
 */
@Repository
public class InMemoryWidgetRepository implements WidgetRepository {

    private static final List<Widget> widgetList = new ArrayList<>();
    protected final AtomicLong counter = new AtomicLong();

    @Override
    public Widget addWidget(Widget widget) {
        widget.setLastModificationDate(LocalDateTime.now());
        widget.setZindex(getNextZIndex(widget.getZindex()));
        widget.setId(counter.incrementAndGet());
        shiftExistingWidgets(widget.getZindex());
        widgetList.add(widget);
        Collections.sort(widgetList);
        return widget;
    }

    @Override
    public Widget updateWidget(Widget widget) {
        widget.setLastModificationDate(LocalDateTime.now());
        int index = widgetList.indexOf(widget);
        if (index < 0) {
            return null;
        }
        shiftExistingWidgets(widget.getZindex());
        widgetList.add(widget);
        Collections.sort(widgetList);
        return widget;
    }

    @Override
    public boolean deleteWidget(Long id) {
        return widgetList.removeIf(x -> x.getId().equals(id));
    }

    @Override
    public Optional<Widget> getById(Long id) {
        return widgetList.stream().filter(x -> x.getId().equals(id)).findFirst();
    }

    @Override
    public List<Widget> getPage(FilterDto dto, int pageSize) {
        return widgetList.stream().filter(x -> x.insideArea(dto)).limit(pageSize).collect(Collectors.toList());
    }

    @Override
    public List<Widget> getAll() {
        return Collections.unmodifiableList(widgetList);
    }

    @Override
    public void clearAll() {
        widgetList.clear();
        counter.set(0L);
    }

    private void shiftExistingWidgets(int zIndex) {
        int current = zIndex;
        List<Long> toBeShiftedId = new ArrayList<>();
        while (true) {
            int finalCurrent = current;
            Optional<Widget> optionalWidget = widgetList.stream().filter(x -> x.getZindex() == finalCurrent).findFirst();
            if (optionalWidget.isEmpty()) {
                break;
            }
            toBeShiftedId.add(optionalWidget.get().getId());
            current++;
        }
        widgetList.stream().filter(x -> toBeShiftedId.contains(x.getId())).forEach(x -> {
            x.setZindex(x.getZindex() + 1);
            x.setLastModificationDate(LocalDateTime.now());
        });
    }

    private Integer getNextZIndex(Integer zindex) {
        if (zindex != null) {
            return zindex;
        }
        if (CollectionUtils.isEmpty(widgetList)) {
            return 0;
        }
        int lastWidgetZIndex = widgetList.get(widgetList.size() - 1).getZindex();
        return lastWidgetZIndex + 1;
    }
}
