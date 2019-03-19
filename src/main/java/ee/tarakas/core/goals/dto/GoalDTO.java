package ee.tarakas.core.goals.dto;

import ee.tarakas.core.goals.dto.validator.CollectedAmount;
import ee.tarakas.core.goals.dto.validator.ImageSize;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

@Getter
@Setter
@CollectedAmount
public class GoalDTO {

    private final static int MINIMUM_PRICE = 0;
    private final static int MAXIMUM_PRICE = 1_000_000;

    private String id;

    @NotEmpty
    private String name;

    @ImageSize
    private byte[] imageSource;

    @Max(value = MAXIMUM_PRICE, message = "Price cannot be more than 1 000 000")
    @Min(value = MINIMUM_PRICE, message = "Price cannot be less than 0")
    private BigDecimal price;

    @Min(value = MINIMUM_PRICE, message = "Collected amount cannot be less than 0")
    private BigDecimal collectedAmount;

}
