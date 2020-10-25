package de.joayahiatene.baseauth.dto;

import lombok.Data;

@Data
public class UserDTO {

    private String username;

    private String password;

    private String firstname;

    private String lastname;

    private String email;
}
