package de.joayahiatene.baseauth.domain.user;

import de.joayahiatene.baseauth.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    User getUserByUsername(String username);

    User createUser(final String username, final String password, final String firstName,
                    final String lastName, final List<String> role, final String email);

    void createPasswordResetTokenForUser(UserDTO userDTO, String token);
    UserDetails loadUserByUsername(String username);

    boolean userExists(String username);

    List<String> getUserRole(String username);
}
