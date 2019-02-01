package ee.tarakas.core.goals;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;

@Getter
@Setter
public class Goal {

    @Id
    private String id;
    private String userId;
    private String name;
    @JsonProperty("imageSource")
    private byte[] imageBase64Bytes;
    private BigDecimal price;
    private BigDecimal collectedAmount;

}
