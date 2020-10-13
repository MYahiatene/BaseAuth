package de.joayahiatene.baseauth.domain.user;

import de.joayahiatene.baseauth.domain.password.RandomPasswordGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public User createUser(String userName, String password, String firstName, String lastName, String role, String email) {
        final RandomPasswordGenerator randomPasswordGenerator = new RandomPasswordGenerator();
        final User user;
        final List<Long> participated = new ArrayList<>();
        // for a completely fresh user to be generated the password value sent from the frontend must be null
        if (password == null) {
            final char[] safePwd = randomPasswordGenerator.generatePwd();
            final String encryptedSafePwd = PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(
                    new String(safePwd));
            user = new User(userName, firstName, lastName, password, email, role);

            final String pwd = new String(safePwd);
           /* final String mailSubject = "Login Daten für Umfrato";
            final String mailMessage = "Sehr geehrte/r Frau/Herr " + lastName + ",\n\n"
                    + "anbei finden Sie Ihre neuen Login Daten. \n\n" + "Username: " + userName + "\n" + "Password: "
                    + pwd + "\n\n" + "Mit freundlichen Grüßen\nIhr Umfrato Team";

            mailService.sendMail(mailSubject, mailMessage, email);
            */

            Arrays.fill(safePwd, '*');
        } else {
            user = new User(userName,firstName,lastName,password,email,role);
        }
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return null;
    }
}
