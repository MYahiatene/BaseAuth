package de.joayahiatene.baseauth.start;

import de.joayahiatene.baseauth.domain.user.User;
import de.joayahiatene.baseauth.domain.user.UserService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AutoConfigureBefore
public class InitializeDatabase implements InitializingBean {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public InitializeDatabase(final UserService userService, final PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void afterPropertiesSet() {
        try {
            userService.loadUserByUsername("test");
        } catch (UsernameNotFoundException e) {
            final String pw = passwordEncoder.encode("password");
            final User user = new User("test", "ervin", "mo", pw, List.of("Admin","User"), "email@domain");
            userService.createUser(user.getUsername(), user.getFirstname(), user.getLastname(), user.getPassword(), user.getRoles(), user.getEmail());
        }
    }
}


