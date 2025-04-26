package com.atlas.tourguide.controllers;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.atlas.tourguide.domain.dtos.AuthResponse;
import com.atlas.tourguide.domain.dtos.LoginRequest;
import com.atlas.tourguide.domain.dtos.SignupRequest;
import com.atlas.tourguide.services.AuthenticationService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
	private final AuthenticationService authenticationService;
	
	@PostMapping("/login")
	public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
		UserDetails userDetails = authenticationService.authenticate(
				loginRequest.getEmail(),
				loginRequest.getPassword()
		);
		String tokenString = authenticationService.generateToken(userDetails);
		AuthResponse authResponse = AuthResponse.builder()
			.token(tokenString)
			.expiresIn(86400)
			.build();
		return ResponseEntity.ok(authResponse);
	}
	
	@PostMapping("/signup")
	public ResponseEntity<AuthResponse> signup(@RequestBody SignupRequest signupRequest) {
		authenticationService.createUser(signupRequest.getEmail(), signupRequest.getPassword(), signupRequest.getName());
		UserDetails userDetails = authenticationService.authenticate(
				signupRequest.getEmail(),
				signupRequest.getPassword()
		);
		String tokenString = authenticationService.generateToken(userDetails);
		AuthResponse authResponse = AuthResponse.builder()
			.token(tokenString)
			.expiresIn(86400)
			.build();
		return ResponseEntity.ok(authResponse);
	}

    @GetMapping("/user_id")
    public ResponseEntity<UUID> getUserId(HttpServletRequest request) {
        UUID userId = (UUID) request.getAttribute("userId");

        if (userId == null) {
            return ResponseEntity.status(401).build(); // Unauthorized if no userId
        }

        return ResponseEntity.ok(userId);
    }
	
	@GetMapping("/check")
	public ResponseEntity<Void> isAuthenticated() {
		return ResponseEntity.ok().build();
	}
}
