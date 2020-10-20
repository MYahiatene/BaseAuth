package de.joayahiatene.baseauth.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PasswordTokenDTO {

  @NotNull
  private String passwordResetToken;

  @NotNull
  private String username;
}
