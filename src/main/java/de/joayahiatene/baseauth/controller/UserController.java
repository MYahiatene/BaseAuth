package de.joayahiatene.baseauth.controller;

import de.joayahiatene.baseauth.domain.profile.FileLocationService;
import de.joayahiatene.baseauth.domain.profile.ProfilePictureRepository;
import de.joayahiatene.baseauth.domain.user.User;
import de.joayahiatene.baseauth.domain.user.UserService;
import de.joayahiatene.baseauth.dto.UserDTO;
import de.joayahiatene.baseauth.response.ValidationResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.Principal;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class UserController {
    private final UserService userService;
    final ProfilePictureRepository profilePictureRepository;
    final FileLocationService fileLocationService;

    Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(UserService userService, ProfilePictureRepository profilePictureRepository, FileLocationService fileLocationService) {
        this.userService = userService;
        this.profilePictureRepository = profilePictureRepository;
        this.fileLocationService = fileLocationService;
    }

    @GetMapping("/checkRole")
    public List<String> getRoles(final Principal principal) {
        return userService.getUserRole(principal.getName());
    }

    @GetMapping("/user/profile")
    public User getUser(final Principal principal) {
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

    @PutMapping("/user/update")
    public ValidationResponse updateUserProfile(@RequestBody @Valid UserDTO userProfileDTO) throws MessagingException {
        ValidationResponse response = new ValidationResponse();
        User user = userService.getUserByUsername(userProfileDTO.getUsername());
        userService.updateUserProfile(user, userProfileDTO);
        response.setValidated(true);

        return response;
    }

    @PostMapping("/profile/picture")
    ValidationResponse uploadImage(@RequestParam MultipartFile profilePicture, Principal principal) throws Exception {
        ValidationResponse validationResponse = new ValidationResponse();
        fileLocationService.save(profilePicture.getBytes(), profilePicture.getOriginalFilename(), principal.getName());
        validationResponse.setValidated(true);
        validationResponse.setSuccessMessage("Uploaded!");
        return validationResponse;
    }

    @GetMapping(value = "profile/picture/")
    ResponseEntity<?> downloadImage(Principal principal) throws IOException {
        User user = getUser(principal);
        logger.warn(fileLocationService.find(user.getProfilePictureID()).toString());

        Path fileSystemResourcePath = Path.of(fileLocationService.find(user.getProfilePictureID()).getPath());
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(fileSystemResourcePath));
        byte[] encode = Base64.getEncoder().encode(resource.getByteArray());
        String result = new String(encode, StandardCharsets.UTF_8);

        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(result);
    }
}
