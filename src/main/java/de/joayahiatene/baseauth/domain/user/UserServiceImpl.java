package de.joayahiatene.baseauth.domain.user;

import de.joayahiatene.baseauth.domain.password.RandomPasswordGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(final UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    @Override
    public User createUser(String userName, String password, String firstName, String lastName, List<String> role, String email) {
        final RandomPasswordGenerator randomPasswordGenerator = new RandomPasswordGenerator();
        final User user;
        // for a completely fresh user to be generated the password value sent from the frontend must be null
        if (password == null) {
            final char[] safePwd = randomPasswordGenerator.generatePwd();
            final String encryptedSafePwd = PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(
                    new String(safePwd));
            user = new User(userName, firstName, lastName, encryptedSafePwd, email, role);
            Arrays.fill(safePwd, '*');
        } else {
            user = new User(userName, firstName, lastName, password, email, role);
        }
        return userRepository.save(user);
    }

    // todo: check type cast
    @Override
    public UserDetails loadUserByUsername(String userName) {
        return (UserDetails) userRepository.findById(userName).orElseThrow(() -> new UsernameNotFoundException("User " + userName + "doesnt  exist!"));

    }
}
