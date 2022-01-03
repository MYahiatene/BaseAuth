package de.joayahiatene.baseauth.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MailDTO {

    @NotNull
    private String username;
}
