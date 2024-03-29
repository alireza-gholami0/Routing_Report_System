package com.example.routingreportsystem.auth;



import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

  private final AuthenticationService service;

  @PostMapping("/register")
  public ResponseEntity<AuthResponse> register(
          @RequestBody RegisterRequest request
  ) {
    return ResponseEntity.status(HttpStatus.OK).body(service.register(request));
  }
  @PostMapping("/log-in")
  public ResponseEntity<AuthResponse> logIn(@RequestBody LoginRequest request) {
    AuthResponse response = service.logIn(request);
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }
}
