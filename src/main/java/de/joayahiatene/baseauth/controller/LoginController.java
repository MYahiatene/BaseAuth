package de.joayahiatene.baseauth.controller;

import de.joayahiatene.baseauth.domain.user.User;
import de.joayahiatene.baseauth.domain.user.UserRepository;
import de.joayahiatene.baseauth.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api")
@RestController
@CrossOrigin
public class LoginController {
    private final UserRepository userRepository;

    @Autowired
    public LoginController(UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    @GetMapping("/checkLogin")
    String checkLogin() {
        return "User:" + userRepository.getOne("test");

    }
}