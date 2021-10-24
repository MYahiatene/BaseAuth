package de.joayahiatene.baseauth.domain.user;

import de.joayahiatene.baseauth.domain.password.PasswordResetToken;
import de.joayahiatene.baseauth.domain.password.PasswordTokenRepository;
import de.joayahiatene.baseauth.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordTokenRepository passwordTokenRepository;

    @Autowired
    public UserServiceImpl(final UserRepository userRepository, final PasswordTokenRepository passwordTokenRepository) {
        this.userRepository = userRepository;
        this.passwordTokenRepository = passwordTokenRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return userRepository.findById(username).orElse(null);
    }

    @Override
    public User createUser(String username, String password, String firstName, String lastName, List<String> role, String email) {
        User user = new User(username, password, firstName, lastName, List.of("User"), email);
        return userRepository.save(user);
    }

    @Override
    public void createPasswordResetTokenForUser(UserDTO userDTO, String token) {
        User user = userRepository.findByUsername(userDTO.getUsername());
        PasswordResetToken myToken = new PasswordResetToken(token, user);
        passwordTokenRepository.save(myToken);
    }
}
