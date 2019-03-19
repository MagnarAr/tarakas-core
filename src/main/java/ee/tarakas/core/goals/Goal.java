package ee.tarakas.core.goals;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
public class Goal implements Serializable {

    @Id
    private String id;

    private String userId;

    private String name;

    @JsonProperty("imageSource")
    private byte[] imageBase64Bytes;

    private BigDecimal price = BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);

    private BigDecimal collectedAmount = BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);

    public BigDecimal getCollectedAmount() {
        return this.collectedAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public void updateCollectedAmountBy(BigDecimal amount) {
        this.collectedAmount = this.collectedAmount.add(amount).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

}
