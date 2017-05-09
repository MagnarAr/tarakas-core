package ee.tarakas.core.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class User {

    @Id
    private String id;
    private String username;
    @JsonIgnore
    private String password;
    private Set<GrantedAuthority> authorities = new HashSet<>();

    public User(String username, String password, Set<GrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    public User(String username, String password, String... extraRoles) {
        this.username = username;
        this.password = password;
        // set roles as authorities
        this.authorities = Arrays.stream(extraRoles)
                .map(r -> new SimpleGrantedAuthority(role(r)))
                .collect(Collectors.toSet());
    }

    private String role(String i) {
        return "ROLE_" + i;
    }

}
