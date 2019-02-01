package ee.tarakas.core.bankaccount;

import ee.tarakas.core.bankaccount.dto.Amount;
import ee.tarakas.core.bankaccount.dto.Balance;
import ee.tarakas.core.security.model.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@Secured(value = "ROLE_USER")
@RequestMapping(value = "/api/balance")
public class BankAccountController {

    private final BankAccountService bankAccountService;

    @Autowired
    public BankAccountController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @GetMapping
    public Balance getBalance(@AuthenticationPrincipal UserContext userDetails) {
        return Balance.of(this.bankAccountService.getUserTotalBalance(userDetails.getUserId()));
    }

    @PostMapping
    public Balance insertDemoMoney(@AuthenticationPrincipal UserContext userDetails, @RequestBody Amount amount) {
        return Balance.of(bankAccountService.insertOrUpdateBalance(userDetails.getUserId(), amount.getAmount()));
    }

}
