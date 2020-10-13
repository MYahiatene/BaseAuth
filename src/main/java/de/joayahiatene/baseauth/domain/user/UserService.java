package de.joayahiatene.baseauth.domain.user;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User createUser(final String username, final String password, final String firstName,
                    final String lastName, final String role, final String email);
}
