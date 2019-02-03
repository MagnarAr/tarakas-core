package ee.tarakas.core;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping(value = "/")
    public String index() {
        return "Greetings from Spring Boot!\n";
    }

}
