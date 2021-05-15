package com.stevenpj.domain;

import java.util.Comparator;
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

    private SortBy sort = SortBy.None;
    private SortOrder sortOrder = SortOrder.DESCENDING;

    public boolean matches(Munro munro) {
        boolean isAtLeastMinHeight = minHeight == null || munro.getHeightInMeters() >= minHeight;
        boolean isLessThanMaxHeight = maxHeight == null || munro.getHeightInMeters() <= maxHeight;
        return hillCategory.matches(munro.getHillCategory())
                && isAtLeastMinHeight
                && isLessThanMaxHeight;
    }

    public int sort(Munro munro, Munro other) {
        Comparator<Munro> comparator = this.sortOrder.applyTo(sort.getComparator());
        return comparator.compare(munro,other);
    }
}