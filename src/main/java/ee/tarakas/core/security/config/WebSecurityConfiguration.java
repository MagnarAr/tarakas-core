package ee.tarakas.core.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import ee.tarakas.core.security.auth.JWTAuthenticationFilter;
import ee.tarakas.core.security.RestAuthenticationEntryPoint;
import ee.tarakas.core.security.auth.LoginProcessingFilter;
import ee.tarakas.core.user.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final String FORM_BASED_LOGIN_ENTRY_POINT = "/api/auth/login";
    private static final String FORM_BASED_REGISTER_ENTRY_POINT = "/api/auth/register";
    private static final String TOKEN_BASED_AUTH_ENTRY_POINT = "/api/**";

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserService userService;
    private final ObjectMapper objectMapper;

    WebSecurityConfiguration(BCryptPasswordEncoder bCryptPasswordEncoder, UserService userService, ObjectMapper objectMapper) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userService = userService;
        this.objectMapper = objectMapper;
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        .cors().and()
        .csrf().disable() // We don't need CSRF for JWT based authentication

        .exceptionHandling()
            .authenticationEntryPoint(new RestAuthenticationEntryPoint())
        
        .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

        .and()
            .authorizeRequests()
                .antMatchers(
                        FORM_BASED_LOGIN_ENTRY_POINT, // Login end-point
                        FORM_BASED_REGISTER_ENTRY_POINT // Register end-point
                ).permitAll()
        .and()
            .authorizeRequests()
                .antMatchers(TOKEN_BASED_AUTH_ENTRY_POINT).authenticated() // Protected API End-points
        .and()
            .addFilterBefore(
                    new LoginProcessingFilter(FORM_BASED_LOGIN_ENTRY_POINT, authenticationManager(), objectMapper),
                    UsernamePasswordAuthenticationFilter.class
            )
            .addFilter(new JWTAuthenticationFilter(authenticationManager()));
    }
}
