package ee.tarakas.core.bankaccount;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class BankAccountService {

    private final BankAccountRepository bankAccountRepository;

    @Autowired
    public BankAccountService(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    public BigDecimal getUserTotalBalance(String userId) {
        return bankAccountRepository.findByUserId(userId).stream()
                .map(BankAccount::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    BigDecimal insertOrUpdateBalance(String userId, BigDecimal amount) {
        List<BankAccount> bankAccountList = bankAccountRepository.findByUserId(userId);
        BankAccount bankAccount = bankAccountList.stream()
                .findFirst().filter(ba -> ba.getType() == BankAccountType.DEMO)
                .orElse(null);
        if (bankAccount == null) {
            throw new IllegalArgumentException("User has no demo account");
        }
        bankAccount.setAmount(bankAccount.getAmount().add(amount));
        return insertOrUpdateBalance(bankAccount).getAmount();
    }

    public BankAccount insertOrUpdateBalance(BankAccount bankAccount) {
        bankAccount.setAmount(bankAccount.getAmount().setScale(2, BigDecimal.ROUND_HALF_UP));
        return bankAccountRepository.save(bankAccount);
    }
}
