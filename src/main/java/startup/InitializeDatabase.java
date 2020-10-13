package startup;

import de.joayahiatene.baseauth.domain.user.User;
import de.joayahiatene.baseauth.domain.user.UserRepository;
import de.joayahiatene.baseauth.domain.user.UserService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@AutoConfigureBefore
public class InitializeDatabase implements InitializingBean {

    private final UserService userService;
    private final UserRepository userRepository;

    @Autowired
    public InitializeDatabase(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @Override
    public void afterPropertiesSet() {
        try {
            userService.loadUserByUsername("test@domain.de");
        } catch (UsernameNotFoundException ex) {
            final User user = userService.createUser("test@domain.de",
                    "password",
                    "Test", "Mastermind", "ROLE_USER", "test@email.de");
            userRepository.save(user);

        }
    }
}
