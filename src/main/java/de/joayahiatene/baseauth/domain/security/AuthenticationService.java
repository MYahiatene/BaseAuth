package de.joayahiatene.baseauth.domain.security;

import de.joayahiatene.baseauth.domain.user.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final JwtTokenService jwtTokenService;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationService(UserRepository userRepository, JwtTokenService jwtTokenService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtTokenService = jwtTokenService;
        this.passwordEncoder = passwordEncoder;
    }

    public JwtTokenResponse generateJWTToken(String username, String password) {
        JwtTokenResponse jwtTokenResponse;
        String status;
        if(userRepository.findByUsername(username) != null && passwordEncoder.matches(password,userRepository.findByUsername(username).getPassword() )) {
            userRepository.findByUsername(username);
            jwtTokenResponse = new JwtTokenResponse(jwtTokenService.generateToken(username), "found");
        } else {
            jwtTokenResponse = new JwtTokenResponse(null, "invalid");
        }
        return jwtTokenResponse;
    }
}