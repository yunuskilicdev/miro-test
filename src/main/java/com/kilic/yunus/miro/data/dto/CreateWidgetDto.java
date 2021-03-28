package com.kilic.yunus.miro.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.Min;

/**
 * @author Yunus Kilic
 */
@Data
@AllArgsConstructor
public class CreateWidgetDto {

    @NonNull
    private int x;

    @NonNull
    private int y;

    private Integer zindex;

    @NonNull
    @Min(1)
    private int width;

    @NonNull
    @Min(1)
    private int height;
}
