package ee.tarakas.core.goals;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class Goal {

    @Id
    String id;
    String userId;
    String name;
    @JsonProperty("imageSource")
    byte[] imageBase64Bytes;
    BigDecimal price;
    BigDecimal collectedAmount;

}
