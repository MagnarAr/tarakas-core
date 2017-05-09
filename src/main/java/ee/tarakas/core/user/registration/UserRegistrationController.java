package ee.tarakas.core.user.registration;


import ee.tarakas.core.security.auth.ajax.LoginRequest;
import ee.tarakas.core.user.User;
import ee.tarakas.core.utils.PasswordUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/auth/register")
public class UserRegistrationController {

    private final UserRegistrationService userRegistrationService;

    @Autowired
    public UserRegistrationController(UserRegistrationService userRegistrationService) {
        this.userRegistrationService = userRegistrationService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public User createUser(@RequestBody LoginRequest request) {
        User user = new User(request.getUsername(), PasswordUtility.generateStrongPassword(request.getPassword()), "USER");
        return userRegistrationService.registerUser(user);
    }
}
