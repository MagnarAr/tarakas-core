package ee.tarakas.core.user.registration;

import ee.tarakas.core.bankaccount.BankAccountService;
import ee.tarakas.core.user.User;
import ee.tarakas.core.user.UserRepository;
import ee.tarakas.core.utils.PasswordUtility;
import org.springframework.stereotype.Service;

@Service
public class UserRegistrationService {

    private final UserRepository userRepository;
    private final BankAccountService bankAccountService;

    UserRegistrationService(UserRepository userRepository, BankAccountService bankAccountService) {
        this.userRepository = userRepository;
        this.bankAccountService = bankAccountService;
    }

    /**
     * Registers user and creates dummy bank account
     * @param username user specified username
     * @param password user specified password
     * @return created user
     */
    User registerUser(String username, String password) {
        User existingUser = userRepository.findByUsername(username);
        if (existingUser != null) {
            throw new IllegalArgumentException("Username already exists!");
        }

        final User user = createUser(username, password);
        bankAccountService.createBankAccount(user);
        return user;
    }

    /**
     * Creates user with hashed password
     * @param username user specified username
     * @param password user specified password
     * @return created user
     */
    private User createUser(String username, String password) {
        User user = new User(username, PasswordUtility.hashPassword(password), "USER");
        return userRepository.save(user);
    }
}
