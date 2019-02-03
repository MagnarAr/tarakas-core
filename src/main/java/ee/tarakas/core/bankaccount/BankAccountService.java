package ee.tarakas.core.bankaccount;

import ee.tarakas.core.user.User;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class BankAccountService {

    private final BankAccountRepository bankAccountRepository;

    BankAccountService(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    BigDecimal getUserTotalBalance(String userId) {
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
            throw new IllegalArgumentException("User has no bank account");
        }
        bankAccount.setAmount(bankAccount.getAmount().add(amount));
        return bankAccountRepository.save(bankAccount).getAmount();
    }

    public void createBankAccount(User user) {
        // create demo account for new user
        BankAccount bankAccount = new BankAccount();
        bankAccount.setUserId(user.getId());
        bankAccount.setAmount(BigDecimal.ZERO);
        bankAccount.setBank(Bank.LHV);
        bankAccount.setAccountNumber("EE001001001001");
        bankAccount.setToken("token");
        bankAccount.setType(BankAccountType.DEMO);
        bankAccountRepository.save(bankAccount);
    }

}
