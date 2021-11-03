package de.joayahiatene.baseauth.controller;

import de.joayahiatene.baseauth.domain.user.User;
import de.joayahiatene.baseauth.domain.user.UserService;
import de.joayahiatene.baseauth.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
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

    @GetMapping("/checkRole")
    public List<String> getRoles(final Principal principal) {
        return userService.getUserRole(principal.getName());
    }

    @GetMapping("/user/profile")
    public User getUser(final Principal principal) {
        logger.warn(principal.getName());
        return userService.getUserByUsername(principal.getName());
    }
}
