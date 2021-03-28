package com.kilic.yunus.miro.data.resource;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Yunus Kilic
 */
@Data
@Builder
public class WidgetResource {

    private Long id;

    private int x;

    private int y;

    private int zindex;

    private int width;

    private int height;

    private LocalDateTime lastModificationDate;
}
