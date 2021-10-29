package de.joayahiatene.baseauth.controller;

import de.joayahiatene.baseauth.domain.user.User;
import de.joayahiatene.baseauth.domain.user.UserService;
import de.joayahiatene.baseauth.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class UserController {
    private final UserService userService;
    Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/checkRole")
    public List<String> getRoles(@RequestBody UserDTO userDTO) {
        return userService.getUserRole(userDTO.getUsername());
    }

    @GetMapping("/user/profile")
    public User loadUser(Authentication authentication) {
        return userService.getUserByUsername(authentication.getName());
    }
}
