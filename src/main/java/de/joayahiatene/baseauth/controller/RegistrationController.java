package de.joayahiatene.baseauth.controller;

import de.joayahiatene.baseauth.domain.user.UserService;
import de.joayahiatene.baseauth.dto.UserDTO;
import de.joayahiatene.baseauth.response.ValidationResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.List;


@RestController
@CrossOrigin
@RequestMapping("/api")
public class RegistrationController {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    Logger logger = LoggerFactory.getLogger(RegistrationController.class);

    public RegistrationController(PasswordEncoder passwordEncoder, UserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @PostMapping("/register")
    public ValidationResponse registerUser(@RequestBody @Valid UserDTO userDTO, BindingResult bindingResult) throws MessagingException {
        ValidationResponse response = new ValidationResponse();
        if (bindingResult.hasErrors()) {
            String error = bindingResult.getFieldErrors().toString();
            response.setValidated(false);
            response.setErrorMessage(error);
        } else if (userService.userExists(userDTO.getUsername())){
            String error = "You are already registered! Please log into your account!";
            response.setValidated(false);
            response.setErrorMessage(error);
        } else {
            createUserAccount(userDTO);
            response.setValidated(true);
            response.setSuccessMessage("Thank you for the registration!");
        }
        return response;
    }


    private void createUserAccount(UserDTO userDTO) {

        String username = userDTO.getUsername();
        String firstname = userDTO.getFirstname();
        String lastname = userDTO.getLastname();
        String password = passwordEncoder.encode(userDTO.getPassword());
        List<String> roles = List.of("User");
        String email = userDTO.getEmail();
        try {
            userService.createUser(username, firstname, lastname, password, roles, email);
        } catch (Exception e) {
            logger.warn("Not possible to create new User");
        }
    }
}
