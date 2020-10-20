package de.joayahiatene.baseauth.domain.password;

public interface PasswordTokenValidationService {
  String validatePasswordResetToken(String username, String token);
}
