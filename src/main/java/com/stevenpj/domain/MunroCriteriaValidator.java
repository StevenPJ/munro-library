package com.stevenpj.domain;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MunroCriteriaValidator  implements ConstraintValidator<ValidMunroCriteria, MunroCriteria> {
    public void initialize(ValidMunroCriteria constraintAnnotation) {
    }

    public boolean isValid(MunroCriteria criteria, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        if (isMaxHeightLessThanMinHeight(criteria)) {
            context
                    .buildConstraintViolationWithTemplate( "Max height should be less than min height" )
                    .addPropertyNode( "maxHeight" )
                    .addConstraintViolation();
            return false;
        }
        return true;
    }

    private boolean isMaxHeightLessThanMinHeight(MunroCriteria criteria) {
        if (criteria.getMinHeight() == null || criteria.getMaxHeight() == null) {
            return false;
        }
        return criteria.getMaxHeight() < criteria.getMinHeight();
    }
}