package com.atlas.tourguide.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.atlas.tourguide.domain.dtos.AuthResponse;
import com.atlas.tourguide.domain.dtos.LoginRequest;
import com.atlas.tourguide.services.AuthenticationService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
	private final AuthenticationService authenticationService;
	
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

}
