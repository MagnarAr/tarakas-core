package ee.tarakas.core.security.auth;

import ee.tarakas.core.security.auth.jwt.JWTFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class JWTAuthenticationFilter extends BasicAuthenticationFilter {

    private static final String HEADER_STRING = "X-Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        final String token = extractToken(req);

        if (token == null) {
            chain.doFilter(req, res);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

    private String extractToken(HttpServletRequest request) {
        String authorizationHeaderValue = request.getHeader(HEADER_STRING);
        if (!StringUtils.startsWith(authorizationHeaderValue, TOKEN_PREFIX)) {
            return null;
        }
        return authorizationHeaderValue.replace(TOKEN_PREFIX, "");
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String token) {
        String user = JWTFactory.verify(token).getSubject();
        if (user != null) {
            return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
        }
        return null;
    }
}
