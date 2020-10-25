package de.joayahiatene.baseauth.domain.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(final UserRepository userRepository) {
        this.userRepository = userRepository;

    }


    @Override
    public UserDetails loadUserByUsername(final String username) {
        return userRepository.findById(username).orElseThrow(() -> new UsernameNotFoundException("Not found"));
    }

    @Override
    public User createUser(final String username, final String password, final String firstName,
                           final String lastName, final List<String> role, final String email) {
        final User user = new User(username, password, firstName, lastName, List.of("User"), email);
        userRepository.save(user);
        return user;
    }
}
