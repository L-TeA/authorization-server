package com.blastza.platform.authorization_server.controller;

import com.blastza.platform.authorization_server.models.AuthenticationRequest;
import com.blastza.platform.authorization_server.models.AuthenticationResponse;
import com.blastza.platform.authorization_server.models.RegisterRequest;
import com.blastza.platform.authorization_server.models.RegistrationResponse;
import com.blastza.platform.authorization_server.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


//@Api(tags = "AUTHORIZATION-SERVER API")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

   // @ApiOperation(value = "Register a user to be able to login into the platform", response = RegistrationResponse.class)
    @PostMapping("/register")
    public ResponseEntity<RegistrationResponse> register(@Valid @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(service.register(request));
    }

/*
    @ApiOperation(value = "Verify if the token is legit and has not yet expired", response = String.class)
    @GetMapping("/verify")
    public ResponseEntity<?> verifyEmail(@RequestParam("token") String token) {
        return ResponseEntity.ok(service.updateUserVerificationStatus(token));
    }

    @PostMapping("/resend-verification")
    public ResponseEntity<?> resendVerificationEmail(@RequestBody String email) {
        return ResponseEntity.ok(service.resendEmailVerification(email));
    }
*/

    //@ApiOperation(value = "Check if the user is authenticated", response = AuthenticationResponse.class)
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }
}
