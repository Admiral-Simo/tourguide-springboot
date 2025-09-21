package com.atlas.tourguide.user;

import com.atlas.tourguide.user.dtos.UserProfileDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController("/api/v1/user")
public class UserController {
  private final UserRepository userRepository;

  public UserController(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @GetMapping("/user_id")
  public ResponseEntity<UUID> getUserId(HttpServletRequest request) {
    UUID userId = (UUID) request.getAttribute("userId");

    if (userId == null) {
      return ResponseEntity.status(401).build(); // Unauthorized if no userId
    }

    return ResponseEntity.ok(userId);
  }

  @GetMapping("/me")
  public ResponseEntity<UserProfileDto> getCurrentUser(HttpServletRequest request) {
    UUID userId = (UUID) request.getAttribute("userId");

    if (userId == null) {
      return ResponseEntity.status(401).build();
    }

    return userRepository.findById(userId).map(user -> {
      UserProfileDto profile = new UserProfileDto(user.getId(), user.getEmail(), user.getName(),
          user.getCreatedAt());
      return ResponseEntity.ok(profile);
    }).orElseGet(() -> ResponseEntity.notFound().build());
  }

  @GetMapping("/check")
  public ResponseEntity<Void> isAuthenticated() {
    return ResponseEntity.ok().build();
  }
}
