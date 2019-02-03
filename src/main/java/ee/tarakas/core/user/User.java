package ee.tarakas.core.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class User implements UserDetails {

    @Id
    private String id;
    private String username;
    private String password;
    private Set<GrantedAuthority> authorities;

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

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
