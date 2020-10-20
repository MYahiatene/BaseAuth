package de.joayahiatene.baseauth.domain.password;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PasswordTokenRepository extends JpaRepository<PasswordResetToken, UUID> {
    PasswordResetToken findByToken(String token);
}
