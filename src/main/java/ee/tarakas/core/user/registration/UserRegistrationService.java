package ee.tarakas.core.user.registration;

import ee.tarakas.core.bankaccount.Bank;
import ee.tarakas.core.bankaccount.BankAccount;
import ee.tarakas.core.bankaccount.BankAccountService;
import ee.tarakas.core.bankaccount.BankAccountType;
import ee.tarakas.core.user.User;
import ee.tarakas.core.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class UserRegistrationService {

    private final UserRepository userRepository;
    private final BankAccountService bankAccountService;

    @Autowired
    public UserRegistrationService(UserRepository userRepository, BankAccountService bankAccountService) {
        this.userRepository = userRepository;
        this.bankAccountService = bankAccountService;
    }

    public User registerUser(User user) {
        User existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser != null) {
            throw new IllegalArgumentException("Username already exists!");
        }
        User registeredUser = userRepository.save(user);
        // create demo account for new user
        BankAccount bankAccount = new BankAccount();
        bankAccount.setUserId(registeredUser.getId());
        bankAccount.setAmount(BigDecimal.ZERO);
        bankAccount.setBank(Bank.LHV);
        bankAccount.setAccountNumber("EE001001001001");
        bankAccount.setToken("token");
        bankAccount.setType(BankAccountType.DEMO);
        bankAccountService.insertOrUpdateBalance(bankAccount);
        return userRepository.save(user);
    }
}
