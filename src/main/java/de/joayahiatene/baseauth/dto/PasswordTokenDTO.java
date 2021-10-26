package de.joayahiatene.baseauth.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;



@Data
public class PasswordTokenDTO {

  @NotNull
  private String passwordResetToken;

  @NotNull
  private String username;
}
