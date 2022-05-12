package mk.finki.ukim.vebprogramiranjestandardnaseminarska193203.model.enumerations;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_USER, ROLE_ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
