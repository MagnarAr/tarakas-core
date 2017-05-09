package ee.tarakas.core.security.endpoint;

import ee.tarakas.core.security.auth.jwt.extractor.TokenExtractor;
import ee.tarakas.core.security.auth.jwt.verifier.TokenVerifier;
import ee.tarakas.core.security.config.JwtSettings;
import ee.tarakas.core.security.config.WebSecurityConfig;
import ee.tarakas.core.security.exceptions.InvalidJwtToken;
import ee.tarakas.core.security.model.UserContext;
import ee.tarakas.core.security.model.token.JwtToken;
import ee.tarakas.core.security.model.token.JwtTokenFactory;
import ee.tarakas.core.security.model.token.RawAccessJwtToken;
import ee.tarakas.core.security.model.token.RefreshToken;
import ee.tarakas.core.user.CustomUserDetailsService;
import ee.tarakas.core.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * RefreshTokenEndpoint
 * 
 * @author vladimir.stankovic
 *
 * Aug 17, 2016
 */
@RestController
public class RefreshTokenEndpoint {
    @Autowired private JwtTokenFactory tokenFactory;
    @Autowired private JwtSettings jwtSettings;
    @Autowired private CustomUserDetailsService userService;
    @Autowired private TokenVerifier tokenVerifier;
    @Autowired @Qualifier("jwtHeaderTokenExtractor") private TokenExtractor tokenExtractor;
    
    @RequestMapping(value="/api/auth/token", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    JwtToken refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String tokenPayload = tokenExtractor.extract(request.getHeader(WebSecurityConfig.JWT_TOKEN_HEADER_PARAM));
        
        RawAccessJwtToken rawToken = new RawAccessJwtToken(tokenPayload);
        RefreshToken refreshToken = RefreshToken.create(rawToken, jwtSettings.getTokenSigningKey()).orElseThrow(InvalidJwtToken::new);

        String jti = refreshToken.getJti();
        if (!tokenVerifier.verify(jti)) {
            throw new InvalidJwtToken();
        }

        String subject = refreshToken.getSubject();
        User user = userService.loadUserByUsername(subject);

        if (user.getAuthorities() == null) {
            throw new InsufficientAuthenticationException("User has no roles assigned");
        }


        UserContext userContext = UserContext.create(user.getId(), user.getUsername(), new ArrayList<>(user.getAuthorities()));

        return tokenFactory.createAccessJwtToken(userContext);
    }
}
