package com.kilic.yunus.miro.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.Min;
import java.time.LocalDateTime;

/**
 * @author Yunus Kilic
 */
@Data
@Builder
@AllArgsConstructor
public class WidgetDto {
    @NonNull
    private Long id;

    @NonNull
    private int x;

    @NonNull
    private int y;

    @NonNull
    private int zindex;

    @NonNull
    @Min(1)
    private int width;

    @NonNull
    @Min(1)
    private int height;

    @NonNull
    private LocalDateTime lastModificationDate;
}