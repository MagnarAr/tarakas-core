package ee.tarakas.core.bankaccount.dto;

import lombok.Getter;

import java.math.BigDecimal;

public class Balance {

    @Getter
    private BigDecimal balance;

    private Balance(BigDecimal balance) {
        this.balance = balance;
    }

    public static Balance of(BigDecimal balance) {
        return new Balance(balance);
    }

}
