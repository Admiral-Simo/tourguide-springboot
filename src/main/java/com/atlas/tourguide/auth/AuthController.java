package com.atlas.tourguide.auth;

import com.atlas.tourguide.auth.dtos.AuthResponseDto;
import com.atlas.tourguide.auth.dtos.LoginRequestDto;
import com.atlas.tourguide.auth.dtos.SignupRequestDto;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.atlas.tourguide.user.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
  private final AuthenticationService authenticationService;
  private final UserRepository userRepository;

  @PostMapping("/login")
  public AuthResponseDto login(@RequestBody LoginRequestDto loginRequestDto) {
    UserDetails userDetails = authenticationService.authenticate(loginRequestDto.getEmail(),
        loginRequestDto.getPassword());
    String tokenString = authenticationService.generateToken(userDetails);
    return AuthResponseDto.builder().token(tokenString).expiresIn(86400).build();
  }

  @PostMapping("/signup")
  public AuthResponseDto signup(@RequestBody SignupRequestDto signupRequest) {
    authenticationService.createUser(signupRequest.getEmail(), signupRequest.getPassword(),
        signupRequest.getName());
    UserDetails userDetails = authenticationService.authenticate(signupRequest.getEmail(),
        signupRequest.getPassword());
    String tokenString = authenticationService.generateToken(userDetails);
    return AuthResponseDto.builder().token(tokenString).expiresIn(86400).build();
  }
}
