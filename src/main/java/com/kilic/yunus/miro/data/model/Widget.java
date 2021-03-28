package com.kilic.yunus.miro.data.model;


import com.kilic.yunus.miro.data.dto.FilterDto;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author Yunus Kilic
 */
@Data
public class Widget implements Comparable<Widget> {
    private Long id;
    private int x;
    private int y;
    private Integer zindex;
    private int width;
    private int height;
    private LocalDateTime lastModificationDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Widget widget = (Widget) o;
        return id.equals(widget.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public int compareTo(Widget w) {
        if (this.getZindex() > w.getZindex()) {
            return 1;
        } else if (this.getZindex() < w.getZindex()) {
            return -1;
        }
        return 0;
    }

    public boolean insideArea(FilterDto dto) {
        int currentLowerX = this.x - (this.getWidth() / 2);
        int currentUpperX = this.x + (this.getWidth() / 2);
        int currentLowerY = this.y - (this.getHeight() / 2);
        int currentUpperY = this.y + (this.getHeight() / 2);
        boolean lowerXCheck = dto.getLowerX() <= currentLowerX;
        boolean lowerYCheck = dto.getLowerY() <= currentLowerY;
        boolean upperXCheck = dto.getUpperX() >= currentUpperX;
        boolean upperYCheck = dto.getUpperY() >= currentUpperY;
        return lowerXCheck && lowerYCheck && upperXCheck && upperYCheck;
    }
}
