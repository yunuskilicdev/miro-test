package com.kilic.yunus.miro.data.repository;

import com.kilic.yunus.miro.data.dto.FilterDto;
import com.kilic.yunus.miro.data.model.Widget;

import java.util.List;
import java.util.Optional;

/**
 * @author Yunus Kilic
 */
public interface WidgetRepository {

    Widget addWidget(Widget widget);

    Widget updateWidget(Widget widget);

    boolean deleteWidget(Long id);

    Optional<Widget> getById(Long id);

    List<Widget> getPage(FilterDto dto, int pageSize);

    List<Widget> getAll();

    void clearAll();
}
