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
    public InitializeDatabase(UserService userService,UserRepository userRepository) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        try {
            userService.loadUserByUsername("test");
        } catch (UsernameNotFoundException e) {
            String pw =PasswordEncoderFactories.createDelegatingPasswordEncoder().encode("password");
            User user = new User("test", "ervin", "mo", pw, "email@domain", List.of("Admin"));
            userRepository.save(user);
        }
    }
}


