package com.example.routingreportsystem.auth;


import com.example.routingreportsystem.domain.User;
import com.example.routingreportsystem.jwt.JwtService;
import com.example.routingreportsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
  private final UserRepository repository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  public AuthResponse register(RegisterRequest request) {

    if(repository.findByEmail(request.email()).isPresent()){
      throw new RuntimeException("The email entered has already been registered");
    }
    else {
      var user = User.builder()
              .email(request.email())
              .password(passwordEncoder.encode(request.password()))
              .role(request.role())
              .build();
      repository.save(user);
      var jwtToken = jwtService.generateToken(user);
      return AuthResponse.builder()
              .token(jwtToken)
              .build();
    }

  }

  public AuthResponse logIn(LoginRequest request) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            request.email(),
            request.password()
        )
    );
    var user = repository.findByEmail(request.email())
            .orElseThrow(() -> new RuntimeException("Email or password is incorrect"));
    var jwtToken = jwtService.generateToken(user);
    return AuthResponse.builder()
        .token(jwtToken)
        .build();
  }
}
