package com.stevenpj.domain;

import javax.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ValidMunroCriteria
public class MunroCriteria {

    private HillCategory hillCategory = HillCategory.EITHER;

    @Min(0)
    private Integer limit;

    @Min(0)
    private Integer minHeight;

    @Min(0)
    private Integer maxHeight;

    private String sort;
    private SortOrder sortOrder = SortOrder.DESCENDING;

    public boolean matches(Munro munro) {
        boolean isAtLeastMinHeight = minHeight == null || munro.getHeightInMeters() >= minHeight;
        boolean isLessThanMaxHeight = maxHeight == null || munro.getHeightInMeters() <= maxHeight;
        return hillCategory.matches(munro.getHillCategory()) && isAtLeastMinHeight && isLessThanMaxHeight;
    }

    public int sort(Munro munro, Munro other) {
        if ("heightInMeters".equals(sort)) {
            return sortByHeightInMeters(munro, other);
        }
        return sortByName(munro, other);
    }

    private int sortByHeightInMeters(Munro munro, Munro other) {
        if (SortOrder.ASCENDING == sortOrder) {
            return munro.getHeightInMeters() - other.getHeightInMeters();
        }
        return other.getHeightInMeters() - munro.getHeightInMeters();
    }

    private int sortByName(Munro munro, Munro other) {
        if (SortOrder.ASCENDING == sortOrder) {
            return munro.getName().compareTo(other.getName());
        }
        return other.getName().compareTo(munro.getName());
    }
}
