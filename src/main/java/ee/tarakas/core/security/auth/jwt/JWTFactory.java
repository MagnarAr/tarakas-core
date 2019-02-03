package ee.tarakas.core.security.auth.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

public class JWTFactory {

    private static final long EXPIRATION_TIME = 864_000_000; // 10 days
    private static final String SECRET = "xm8EV6Hy5RMFK4EEACIDAwQus";

    public static String create(String subject) {
        return JWT.create()
                .withSubject(subject)
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(HMAC512(SECRET.getBytes()));
    }

    public static DecodedJWT verify(String token) {
        return JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
                .build()
                .verify(token);
    }
}
