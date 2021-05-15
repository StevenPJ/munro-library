package com.stevenpj.domain;

import javax.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MunroCriteria {

    private HillCategory hillCategory = HillCategory.EITHER;

    @Min(0)
    private Integer limit;

    private Integer minHeight;

    public boolean matches(Munro munro) {
        boolean isAtLeastMinHeight = minHeight == null || munro.getHeightInMeters() >= minHeight;
        return hillCategory.matches(munro.getHillCategory()) && isAtLeastMinHeight;
    }
}
