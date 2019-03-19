package ee.tarakas.core.goals.dto.validator;

import ee.tarakas.core.goals.dto.GoalDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CollectedAmountValidator implements ConstraintValidator<CollectedAmount, GoalDTO> {

    @Override
    public void initialize(CollectedAmount constraintAnnotation) {

    }

    @Override
    public boolean isValid(GoalDTO goalDto, ConstraintValidatorContext context) {
        // price not set, always return not valid
        if (goalDto.getPrice() == null) {
            return false;
        }
        // collected amount not set means it is valid
        if (goalDto.getCollectedAmount() == null) {
            return true;
        }
        return goalDto.getCollectedAmount().compareTo(goalDto.getPrice()) <= 0;
    }
}
