package ee.tarakas.core.goals.dto;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.math.BigDecimal;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

class GoalDTOValidationIntegrationTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
        validator = vf.getValidator();
    }

    @Test
    void testValidGoal_passesValidation() {
        GoalDTO goalDTO = getValidGoal();
        Set<ConstraintViolation<GoalDTO>> violations = validator.validate(goalDTO);
        assertTrue(violations.isEmpty());
    }

    @Test
    void testGoal_invalidPrice_failsValidation() {
        GoalDTO goalDTO = getValidGoal();
        goalDTO.setPrice(BigDecimal.valueOf(-10));

        Set<ConstraintViolation<GoalDTO>> violations = validator.validate(goalDTO);
        // TODO: assertions
    }

    // TODO: add negative case tests

    private static GoalDTO getValidGoal() {
        GoalDTO goalDTO = new GoalDTO();
        goalDTO.setName("Goal");
        goalDTO.setPrice(BigDecimal.TEN);
        goalDTO.setCollectedAmount(BigDecimal.ONE);
        return goalDTO;
    }

}
