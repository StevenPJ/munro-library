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

    private Integer minHeight;
    private Integer maxHeight;

    public boolean matches(Munro munro) {
        boolean isAtLeastMinHeight = minHeight == null || munro.getHeightInMeters() >= minHeight;
        boolean isLessThanMaxHeight = maxHeight == null || munro.getHeightInMeters() <= maxHeight;
        return hillCategory.matches(munro.getHillCategory()) && isAtLeastMinHeight && isLessThanMaxHeight;
    }
}
