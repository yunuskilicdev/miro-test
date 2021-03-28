package com.kilic.yunus.miro.data.mapper;

import com.kilic.yunus.miro.data.dto.CreateWidgetDto;
import com.kilic.yunus.miro.data.dto.WidgetDto;
import com.kilic.yunus.miro.data.model.Widget;
import com.kilic.yunus.miro.data.resource.WidgetResource;
import org.mapstruct.Mapper;

/**
 * @author Yunus Kilic
 */
@Mapper
public interface WidgetMapper {

    WidgetResource toResource(Widget widget);

    Widget toModel(WidgetDto widgetDto);

    Widget toModelFromCreate(CreateWidgetDto createWidgetDto);
}
