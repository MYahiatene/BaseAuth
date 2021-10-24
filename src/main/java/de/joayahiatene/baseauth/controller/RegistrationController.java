package de.joayahiatene.baseauth.controller;

import de.joayahiatene.baseauth.domain.user.UserService;
import de.joayahiatene.baseauth.dto.UserDTO;
import de.joayahiatene.baseauth.response.ValidationResponse;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
public class RegistrationController {

    private final UserService userService;

    public RegistrationController(final UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ValidationResponse userRegistration(@RequestBody @Valid UserDTO userDTO, BindingResult bindingResult) {
        final ValidationResponse validationResponse = new ValidationResponse();
        System.out.print(userDTO.getUsername());
        if (bindingResult.hasErrors()) {
            final String error = bindingResult.getFieldErrors().toString();
            validationResponse.setValidated(false);
            validationResponse.setErrorMessage(error);
        } else if (userService.loadUserByUsername(userDTO.getUsername()) != null) {
            final String error = "You are already registered! Please log into your account!";
            validationResponse.setValidated(false);
            validationResponse.setErrorMessage(error);
        } else if (userService.loadUserByUsername(userDTO.getUsername()) == null) {
            userService.createUser(userDTO.getUsername(), userDTO.getPassword(), userDTO.getFirstname(),
                    userDTO.getLastname(), List.of("User"), userDTO.getEmail());
            validationResponse.setValidated(true);
            validationResponse.setSuccessMessage("Thank you for the registration!");
        }
        return validationResponse;
    }
}
