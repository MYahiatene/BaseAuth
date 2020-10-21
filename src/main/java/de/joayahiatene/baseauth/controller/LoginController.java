package de.joayahiatene.baseauth.controller;

import de.joayahiatene.baseauth.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/api")
@RestController
@CrossOrigin
public class LoginController {
    private final UserRepository userRepository;

    @Autowired
    public LoginController(final UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    @GetMapping("/login")
    public String checkLogin() {
        return "User:" + userRepository.getOne("test");

    }
}
