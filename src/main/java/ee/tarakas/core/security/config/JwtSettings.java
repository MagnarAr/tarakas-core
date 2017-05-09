package ee.tarakas.core.security.config;

import lombok.Data;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class JwtSettings {
    /**
     * {@link JwtToken} will expire after this time.
     */
    private Integer tokenExpirationTime = 30;

    /**
     * Token issuer.
     */
    private String tokenIssuer = "TARAKAS-CORE";
    
    /**
     * Key is used to sign {@link JwtToken}.
     */
    private String tokenSigningKey = "xm8EV6Hy5RMFK4EEACIDAwQus";
    
    /**
     * {@link JwtToken} can be refreshed during this timeframe.
     */
    private Integer refreshTokenExpTime = 60;
}
