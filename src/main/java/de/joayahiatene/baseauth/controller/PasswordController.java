package de.joayahiatene.baseauth.controller;

import de.joayahiatene.baseauth.domain.password.PasswordTokenValidationService;
import de.joayahiatene.baseauth.domain.user.UserService;
import de.joayahiatene.baseauth.dto.PasswordTokenDTO;
import de.joayahiatene.baseauth.dto.UserDTO;
import de.joayahiatene.baseauth.response.ValidationResponse;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.UUID;
@RestController
@CrossOrigin
public class PasswordController {

    private final UserService userService;
    private final PasswordTokenValidationService passwordTokenValidationService;

    public PasswordController(final UserService userService, final PasswordTokenValidationService passwordTokenValidationService) {
        this.userService = userService;
        this.passwordTokenValidationService = passwordTokenValidationService;
    }

    @PostMapping("/user/password/reset")
    public ValidationResponse resetPassword(@RequestBody @Valid UserDTO userDTO, BindingResult bindingResult) throws UnknownHostException {
        ValidationResponse response = new ValidationResponse();

        if (userService.loadUserByUsername(userDTO.getUsername()) != null && !bindingResult.hasErrors()) {
            response.setValidated(true);
            String token = UUID.randomUUID().toString();
            userService.createPasswordResetTokenForUser(userDTO, token);

            String url = constructResetTokenLink(token, userDTO);
            response.setSuccessMessage(url);
            // emailService.sendEmail(userDTO.getUsername(), "Password-Reset", url);


        } else if (bindingResult.hasErrors()) {
            String error = bindingResult.getFieldErrors().toString();
            response.setValidated(false);
            response.setErrorMessage(error);
        } else {
            String error = "Following E-Mail-Adress does not exist! Please re-enter!";
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
            response.setSuccessMessage("Validation successfull");
        } else if (result.equals("invalidToken") || result.equals("expired")) {
            response.setValidated(false);
            response.setErrorMessage("Token is invalid");
        }
        return response;
    }

    /*
    @PostMapping("/user/password/change")
    public ValidationResponse resetPassword(@RequestBody @Valid UserDTO userDTO) {
        ValidationResponse response = new ValidationResponse();
        User user = userService.loadUserByUsername(userDTO.getUsername());
        userService.updateUserPassword(user, passwordEncoder.encode(userProfileDTO.getNewPassword()));
        response.setValidated(true);
        response.setSuccessMessage("Password changed");
        emailService.sendEmail(user.getUsername(),"Password-change", "Your Password has been changed");

        return response;
    }

     */


    private String constructResetTokenLink(String token, UserDTO userDTO) {
        String frontendPort = "3000";
        return "http://" + InetAddress.getLoopbackAddress().getHostName() + ":" + frontendPort + "/reset?username=" + userDTO.getUsername() + "&token=" + token;
    }


}
