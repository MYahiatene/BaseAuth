package de.joayahiatene.baseauth.controller;

import de.joayahiatene.baseauth.Exception.EntityNotFoundException;
import de.joayahiatene.baseauth.domain.security.AuthenticationService;
import de.joayahiatene.baseauth.domain.security.JwtTokenResponse;
import de.joayahiatene.baseauth.dto.UserDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;


@RestController
@CrossOrigin
public class LoginController {
    private final AuthenticationService authenticationService;

    public LoginController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity createCustomer(@RequestBody @Valid UserDTO userDTO) {

        JwtTokenResponse jwtTokenResponse = authenticationService.generateJWTToken(userDTO.getUsername(), userDTO.getPassword());
        if (Objects.equals(jwtTokenResponse.getStatus(), "found")) {
            return new ResponseEntity<>(jwtTokenResponse, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User not found! Please correct the input or register a new account!", HttpStatus.OK);
        }
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity handleEntityNotFoundException(EntityNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}
