package ee.tarakas.core.goals.dto.validator;

import ee.tarakas.core.goals.dto.GoalDTO;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CollectedAmountValidatorTest {

    private CollectedAmountValidator collectedAmountValidator = new CollectedAmountValidator();

    @Test
    void whenCollectedAmountLessThanPrice_validatorReturnsTrue() {
        GoalDTO goalDTO = createGoalDTO(30, 20);
        boolean isValid = collectedAmountValidator.isValid(goalDTO, null);
        assertTrue(isValid);
    }

    @Test
    void whenCollectedAmountEqualToPrice_validatorReturnsTrue() {
        GoalDTO goalDTO = createGoalDTO(30, 30);
        boolean isValid = collectedAmountValidator.isValid(goalDTO, null);
        assertTrue(isValid);
    }

    @Test
    void whenCollectedAmountGreaterThanPrice_validatorReturnsFalse() {
        GoalDTO goalDTO = createGoalDTO(20, 30);
        boolean isValid = collectedAmountValidator.isValid(goalDTO, null);
        assertFalse(isValid);
    }

    private static GoalDTO createGoalDTO(int price, int collectedAmount) {
        GoalDTO goalDTO = new GoalDTO();
        goalDTO.setCollectedAmount(BigDecimal.valueOf(collectedAmount));
        goalDTO.setPrice(BigDecimal.valueOf(price));
        return goalDTO;
    }

}