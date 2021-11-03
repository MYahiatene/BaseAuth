package de.joayahiatene.baseauth.domain.user;

import de.joayahiatene.baseauth.domain.password.PasswordResetToken;
import de.joayahiatene.baseauth.domain.password.PasswordTokenRepository;
import de.joayahiatene.baseauth.domain.profile.UserProfileGravatarHash;
import de.joayahiatene.baseauth.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        return userRepository.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException("User name " + username + " not found."));
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User createUser(String username, String password, String firstName, String lastName, List<String> role, String email) {
        String hash = UserProfileGravatarHash.md5Hex(username);
        final User user = new User(username, password, firstName, lastName, role, email, hash);
        return userRepository.save(user);
    }

    @Override
    public void createPasswordResetTokenForUser(UserDTO userDTO, String token) {
        User user = userRepository.findByUsername(userDTO.getUsername());
        PasswordResetToken myToken = new PasswordResetToken(token, user);
        passwordTokenRepository.save(myToken);
    }

    @Override
    public boolean userExists(String username) {
        return userRepository.existsUserByUsername(username);
    }

    @Override
    public void updateUserPassword(User user, String password) {
        user.setPassword(password);
        userRepository.save(user);
    }

    @Override
    public List<String> getUserRole(String username) {
        return userRepository.findById(username).orElseThrow().getRoles();
    }

    @Override
    public void deleteUser(String username) {
        User user = userRepository.getById(username);
        userRepository.delete(user);
    }
}
