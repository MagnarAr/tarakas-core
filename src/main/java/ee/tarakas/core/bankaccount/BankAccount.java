package ee.tarakas.core.bankaccount;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class BankAccount {

    @Id
    String id;
    String userId;
    BigDecimal amount;
    String accountNumber;
    Bank bank;
    String token;
    BankAccountType type;

}
