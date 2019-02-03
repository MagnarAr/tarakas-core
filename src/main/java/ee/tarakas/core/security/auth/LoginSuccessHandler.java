package ee.tarakas.core.security.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import ee.tarakas.core.security.auth.jwt.JWTFactory;
import ee.tarakas.core.user.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final ObjectMapper mapper;

    LoginSuccessHandler(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        final String subject = ((User) authentication.getPrincipal()).getId();
        final String token = JWTFactory.create(subject);
        sendTokenInResponseBody(token, response);
    }

    private void sendTokenInResponseBody(String token, HttpServletResponse response) throws IOException {
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);

        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        mapper.writeValue(response.getWriter(), tokenMap);
    }

}
