package de.joayahiatene.baseauth.controller;

import de.joayahiatene.baseauth.domain.user.UserService;
import de.joayahiatene.baseauth.dto.UserDTO;
import de.joayahiatene.baseauth.response.ValidationResponse;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/api")
@Controller
@CrossOrigin
public class RegistrationController {

    private final UserService userService;

    public RegistrationController(final UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ValidationResponse userRegistration(final @Valid UserDTO userDTO, final BindingResult bindingResult) {
        final ValidationResponse validationResponse = new ValidationResponse();
        if (bindingResult.hasErrors()) {
            final String error = bindingResult.getFieldErrors().toString();
            validationResponse.setValidated(false);
            validationResponse.setErrorMessage(error);
        } else if (userService.loadUserByUsername(userDTO.getUsername()) != null) {
            final String error = "You are already registered! Please log into your account!";
            validationResponse.setValidated(false);
            validationResponse.setErrorMessage(error);
        } else {
            userService.createUser(userDTO.getUsername(), userDTO.getPassword(), userDTO.getFirstname(),
                    userDTO.getLastname(), List.of("User"), userDTO.getEmail());
            validationResponse.setValidated(true);
            validationResponse.setSuccessMessage("Thank you for the registration!");
        }
        return validationResponse;
    }
}
