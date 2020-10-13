package de.joayahiatene.baseauth.controller;

import de.joayahiatene.baseauth.domain.user.UserService;
import de.joayahiatene.baseauth.dto.UserDTO;
import de.joayahiatene.baseauth.response.ValidationResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@CrossOrigin
@PreAuthorize("hasRole('ROLE_ANONYMOUS')")
public class RegistrationController {

    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ValidationResponse userRegistration(@Valid UserDTO userDTO, BindingResult bindingResult) {
        ValidationResponse validationResponse = new ValidationResponse();
        if (bindingResult.hasErrors()) {
            String error = bindingResult.getFieldErrors().toString();
            validationResponse.setValidated(false);
            validationResponse.setErrorMessage(error);
        } else if (userService.loadUserByUsername(userDTO.getUsername()) != null) {
            String error = "You are already registered! Please log into your account!";
            validationResponse.setValidated(false);
            validationResponse.setErrorMessage(error);
        } else {
            userService.createUser(userDTO.getUsername(), userDTO.getPassword(), userDTO.getFirstName(),
                    userDTO.getLastName(), "ROLE_USER", userDTO.getEmail());
            validationResponse.setValidated(true);
            validationResponse.setSuccessMessage("Thank you for the registration!");
        }
        return validationResponse;
    }
}
