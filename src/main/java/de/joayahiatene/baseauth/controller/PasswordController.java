package de.joayahiatene.baseauth.controller;

import de.joayahiatene.baseauth.domain.password.PasswordTokenValidationService;
import de.joayahiatene.baseauth.domain.user.User;
import de.joayahiatene.baseauth.domain.user.UserService;
import de.joayahiatene.baseauth.dto.PasswordTokenDTO;
import de.joayahiatene.baseauth.dto.UserDTO;
import de.joayahiatene.baseauth.response.ValidationResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class PasswordController {

    private final UserService userService;
    private final PasswordTokenValidationService passwordTokenValidationService;
    private final PasswordEncoder passwordEncoder;
    Logger logger = LoggerFactory.getLogger(PasswordController.class);


    public PasswordController(final UserService userService, final PasswordTokenValidationService passwordTokenValidationService, final PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordTokenValidationService = passwordTokenValidationService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/user/password/reset")
    public ValidationResponse resetPassword(@RequestBody @Valid UserDTO userDTO, BindingResult bindingResult) {
        ValidationResponse response = new ValidationResponse();

        if (userService.loadUserByUsername(userDTO.getUsername()) != null && !bindingResult.hasErrors()) {
            response.setValidated(true);
            String token = UUID.randomUUID().toString();
            userService.createPasswordResetTokenForUser(userDTO, token);

            String url = constructResetTokenLink(token, userDTO);
            response.setSuccessMessage(url);


        } else if (bindingResult.hasErrors()) {
            String error = bindingResult.getFieldErrors().toString();
            logger.warn(error);
            response.setValidated(false);
            response.setErrorMessage(error);
        } else {
            String error = "Following E-Mail-Address does not exist! Please re-enter!";
            logger.warn(error);
            response.setValidated(false);
            response.setErrorMessage(error);
        }

        return response;
    }

    @PostMapping("/user/password/validate")
    public ValidationResponse checkToken(@RequestBody @Valid PasswordTokenDTO passwordTokenDTO) {
        ValidationResponse response = new ValidationResponse();

        String result = passwordTokenValidationService.validatePasswordResetToken(passwordTokenDTO.getUsername(), passwordTokenDTO.getPasswordResetToken());
        if (result == null) {
            response.setValidated(true);
            response.setSuccessMessage("Validation successful");
        } else if (result.equals("invalidToken") || result.equals("expired")) {
            String error = "Token is invalid";
            logger.warn(error);
            response.setValidated(false);
            response.setErrorMessage(error);
        }
        return response;
    }


    @PostMapping("/user/password/replace")
    public ValidationResponse resetPassword(@RequestBody @Valid UserDTO userDTO) {
        ValidationResponse response = new ValidationResponse();
        User user = userService.getUserByUsername(userDTO.getUsername());
        userService.updateUserPassword(user, passwordEncoder.encode(userDTO.getNewPassword()));
        response.setValidated(true);
        response.setSuccessMessage("Password changed");

        return response;
    }

    @PostMapping("/user/password/update")
    public ValidationResponse changeUserPasswordIfOldIsValid(@RequestBody @Valid UserDTO userDTO,
                                                             Authentication authentication) {
        ValidationResponse response = new ValidationResponse();
        User user = userService.getUserByUsername(authentication.getName());
        String userPassword = user.getPassword();

        if (passwordEncoder.matches(userDTO.getOldPassword(), userPassword)) {
            userService.updateUserPassword(user, passwordEncoder.encode(userDTO.getNewPassword()));

            response.setValidated(true);
            response.setSuccessMessage("Password changed");

        } else {
            response.setValidated(false);
            String error = "Your old-password doesn't match! Please try again";
            logger.warn(error);
            response.setErrorMessage(error);
        }
        return response;
    }


    private String constructResetTokenLink(String token, UserDTO userDTO) {
        String frontendPort = "3000";
        String urlPrefix = "http://";
        return urlPrefix + InetAddress.getLoopbackAddress().getHostName() + ":" + frontendPort + "/reset?username=" + userDTO.getUsername() + "&token=" + token;
    }


}
