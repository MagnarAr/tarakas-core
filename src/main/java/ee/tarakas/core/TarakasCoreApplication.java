package ee.tarakas.core;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class TarakasCoreApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(TarakasCoreApplication.class, args);
    }

    @Override
    public void run(String... args) {

    }

    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
