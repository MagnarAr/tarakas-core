package ee.tarakas.core.user.registration;

import ee.tarakas.core.bankaccount.BankAccountService;
import ee.tarakas.core.user.User;
import ee.tarakas.core.user.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserRegistrationService {

    private final UserRepository userRepository;
    private final BankAccountService bankAccountService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    UserRegistrationService(UserRepository userRepository, BankAccountService bankAccountService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bankAccountService = bankAccountService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    /**
     * Registers user and creates dummy bank account
     * @param username user specified username
     * @param password user specified password
     */
    void registerUser(String username, String password) {
        User existingUser = userRepository.findByUsername(username);
        if (existingUser != null) {
            throw new IllegalArgumentException("Username already exists!");
        }

        final User user = createUser(username, password);
        bankAccountService.createBankAccount(user);
    }

    /**
     * Creates user with hashed password
     * @param username user specified username
     * @param password user specified password
     * @return created user
     */
    private User createUser(String username, String password) {
        final String encodedPassword = bCryptPasswordEncoder.encode(password);
        User user = new User(username, encodedPassword, "USER");
        return userRepository.save(user);
    }
}
