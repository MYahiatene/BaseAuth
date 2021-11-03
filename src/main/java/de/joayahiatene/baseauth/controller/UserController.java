package de.joayahiatene.baseauth.controller;

import de.joayahiatene.baseauth.domain.user.User;
import de.joayahiatene.baseauth.domain.user.UserService;
import de.joayahiatene.baseauth.dto.UserDTO;
import de.joayahiatene.baseauth.response.ValidationResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
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

    @DeleteMapping("user/profile/delete/{username}")
    public ValidationResponse deleteEmployee(@PathVariable(value = "username") String username) {
        ValidationResponse response = new ValidationResponse();

        if (!userService.userExists(username)) {
            String error = "Failed to locate your account! Please contact your admin!";
            response.setValidated(false);
            response.setErrorMessage(error);
        } else {
            userService.deleteUser(username);
            String message = "Account deleted! Hope to see you soon! in 10 seconds your session will be terminated!";
            response.setValidated(true);
            response.setSuccessMessage(message);
        }
        return response;
    }


}
