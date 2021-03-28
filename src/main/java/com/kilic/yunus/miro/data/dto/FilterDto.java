package com.kilic.yunus.miro.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Yunus Kilic
 */
@Data
@AllArgsConstructor
public class FilterDto {
    private int lowerX;
    private int lowerY;
    private int upperX;
    private int upperY;
}
