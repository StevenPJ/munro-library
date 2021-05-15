package com.stevenpj.domain;

import io.micrometer.core.instrument.util.StringUtils;
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

    public boolean matches(Munro munro) {
        boolean isNotBlank = StringUtils.isNotBlank(munro.getHillCategory());
        return isNotBlank && hillCategory.matches(munro.getHillCategory());
    }
}
