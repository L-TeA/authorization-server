package com.blastza.platform.authorization_server.models;

import lombok.*;

@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationResponse {
    private String token;
    private String message;
}
