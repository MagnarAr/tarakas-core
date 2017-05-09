package ee.tarakas.core.bankaccount;

import ee.tarakas.core.security.model.UserContext;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@Secured(value = "ROLE_USER")
@RequestMapping(value = "/api/balance")
public class BankAccountController {

    private final BankAccountService bankAccountService;

    @Autowired
    public BankAccountController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Balance getBalance(@AuthenticationPrincipal UserContext userDetails) {
        return new Balance(this.bankAccountService.getUserTotalBalance(userDetails.getUserId()));
    }

    @RequestMapping(method = RequestMethod.POST)
    public Balance insertDemoMoney(@AuthenticationPrincipal UserContext userDetails, @RequestBody Amount amount) {
        return new Balance(bankAccountService.insertOrUpdateBalance(userDetails.getUserId(), amount.getAmount()));
    }

    @AllArgsConstructor
    static class Balance {

        @Getter
        @Setter
        BigDecimal balance;
    }

    static class Amount {
        @Getter
        @Setter
        BigDecimal amount;
    }
}
