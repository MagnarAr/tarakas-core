package ee.tarakas.core.bankaccount;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Builder
@Getter
class BankAccount {

    @Id
    private String id;
    private String userId;

    @Builder.Default
    private BigDecimal amount = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);;
    private String accountNumber;
    private Bank bank;
    private String token;
    private BankAccountType type;

    public BigDecimal getAmount() {
        return this.amount.setScale(2, RoundingMode.HALF_UP);
    }

    public void updateAmountBy(BigDecimal amount) {
    	this.amount = this.amount.add(amount).setScale(2, RoundingMode.HALF_UP);
		}

}
