package ee.tarakas.core.user.registration;

import ee.tarakas.core.security.auth.ajax.LoginRequest;
import ee.tarakas.core.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/auth/register")
public class UserRegistrationController {

    private final UserRegistrationService userRegistrationService;

    @Autowired
    public UserRegistrationController(UserRegistrationService userRegistrationService) {
        this.userRegistrationService = userRegistrationService;
    }

    @PostMapping
    public User createUser(@RequestBody LoginRequest request) {
        return userRegistrationService.registerUser(request.getUsername(), request.getPassword());
    }

}
