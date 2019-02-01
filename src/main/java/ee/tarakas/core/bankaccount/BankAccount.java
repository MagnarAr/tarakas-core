package ee.tarakas.core.bankaccount;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;

@Getter
@Setter
class BankAccount {

    @Id
    private String id;
    private String userId;
    private BigDecimal amount;
    private String accountNumber;
    private Bank bank;
    private String token;
    private BankAccountType type;

    public void setAmount(BigDecimal amount) {
        this.amount = amount.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

}
