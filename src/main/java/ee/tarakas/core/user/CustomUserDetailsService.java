package ee.tarakas.core.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService {

    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ROLE_USER = "USER";

    private UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private User getUserFromDB(String username) {
        return userRepository.findByUsername(username);
    }

    public User loadUserByUsername(String username) {
        return getUserFromDB(username);
    }
}
