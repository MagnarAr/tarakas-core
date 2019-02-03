package ee.tarakas.core.bankaccount;

import ee.tarakas.core.bankaccount.dto.Amount;
import ee.tarakas.core.bankaccount.dto.Balance;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@Secured(value = "ROLE_USER")
@RequestMapping(value = "/api/balance")
public class BankAccountController {

    private final BankAccountService bankAccountService;

    BankAccountController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @GetMapping
    public Balance getBalance(@AuthenticationPrincipal String userId) {
        return Balance.of(this.bankAccountService.getUserTotalBalance(userId));
    }

    @PostMapping
    public Balance insertDemoMoney(@AuthenticationPrincipal String userId, @RequestBody Amount amount) {
        return Balance.of(bankAccountService.insertOrUpdateBalance(userId, amount.getAmount()));
    }

}
