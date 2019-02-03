package ee.tarakas.core.security.auth;

import lombok.Getter;

@Getter
public class LoginRequest {

    private String username;
    private String password;

}
