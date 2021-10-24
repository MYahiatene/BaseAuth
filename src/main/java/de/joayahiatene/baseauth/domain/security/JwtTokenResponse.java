package de.joayahiatene.baseauth.domain.security;

import lombok.Data;

@Data
public class JwtTokenResponse {
    private String token;
    private String status;
    public JwtTokenResponse(String token, String status) {
        this.token = token;
        this.status = status;
    }
}
