package de.joayahiatene.baseauth.start;

import de.joayahiatene.baseauth.domain.user.User;
import de.joayahiatene.baseauth.domain.user.UserRepository;
import de.joayahiatene.baseauth.domain.user.UserService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AutoConfigureBefore
public class InitializeDatabase implements InitializingBean {

    private final UserRepository userRepository;
    private final UserService userService;

    @Autowired
    public InitializeDatabase(final UserService userService, final UserRepository userRepository) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Override
    public void afterPropertiesSet() {
        try {
            userService.loadUserByUsername("test");
        } catch (UsernameNotFoundException e) {
            final String pw = PasswordEncoderFactories.createDelegatingPasswordEncoder().encode("password");
            final User user = new User("test", "ervin", "mo", pw, List.of("Admin"), "email@domain");
            userRepository.save(user);
        }
    }
}


