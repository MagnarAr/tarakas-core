package ee.tarakas.core.user.registration;

import ee.tarakas.core.security.auth.LoginRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/auth/register")
public class UserRegistrationController {

    private final UserRegistrationService userRegistrationService;

    UserRegistrationController(UserRegistrationService userRegistrationService) {
        this.userRegistrationService = userRegistrationService;
    }

    @PostMapping
    public void createUser(@RequestBody LoginRequest request) {
        userRegistrationService.registerUser(request.getUsername(), request.getPassword());
    }

}
