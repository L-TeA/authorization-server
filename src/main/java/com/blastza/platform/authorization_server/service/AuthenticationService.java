package com.blastza.platform.authorization_server.service;

import com.blastza.platform.authorization_server.config.JwtService;
import com.blastza.platform.authorization_server.exception.UserAlreadyExistsException;
import com.blastza.platform.authorization_server.models.AuthenticationRequest;
import com.blastza.platform.authorization_server.models.AuthenticationResponse;
import com.blastza.platform.authorization_server.models.RegisterRequest;
import com.blastza.platform.authorization_server.models.RegistrationResponse;
import com.blastza.platform.authorization_server.repository.UserRepository;
import com.blastza.platform.authorization_server.user.Role;
import com.blastza.platform.authorization_server.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    //private final EmailService emailService;

    public RegistrationResponse register(RegisterRequest request) {
        validateEmailNotTaken(request.getEmail());

        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)  // Assuming Role is an enum
                .verificationToken(UUID.randomUUID().toString())
                .isVerified(false)
                .build();
        userRepository.save(user);
       // emailService.sendVerificationEmail(user.getEmail(), user.getVerificationToken());
        var jwtToken = jwtService.generateToken(user);

        return RegistrationResponse.builder()
                .token(jwtToken)
                .message("Please check your email for verification.")
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

/*    public ResponseEntity<?> updateUserVerificationStatus(String token) {
        Optional<User> userOptional = userRepository.findByVerificationToken(token);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setVerified(true);
            user.setVerificationToken(null); // Clear token after verification
            userRepository.save(user);
            return ResponseEntity.ok("Email verified successfully.");
        }
        return ResponseEntity.badRequest().body("Invalid token.");
    }*/

/*    public ResponseEntity<?> resendEmailVerification(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent() && !userOptional.get().isVerified()) {
            User user = userOptional.get();
            user.setVerificationToken(UUID.randomUUID().toString());
            userRepository.save(user);
            emailService.sendVerificationEmail(user.getEmail(), user.getVerificationToken());
            return ResponseEntity.ok("Verification email resent.");
        }
        return ResponseEntity.badRequest().body("User not found or already verified.");
    }*/

    private void validateEmailNotTaken(String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new UserAlreadyExistsException("This email already exists on our system");
        }
    }
}
