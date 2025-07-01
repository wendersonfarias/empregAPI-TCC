package me.wendersonfarias.empregapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.wendersonfarias.empregapi.docs.AuthControllerDocs;
import me.wendersonfarias.empregapi.dto.LoginRequest;
import me.wendersonfarias.empregapi.dto.LoginResponse;
import me.wendersonfarias.empregapi.service.AuthService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController implements AuthControllerDocs {

  private final AuthService authService;

  @Override
  @PostMapping("/login") // Responde a POST /api/auth/login
  public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
    String token = authService.authenticate(request.getEmail(), request.getSenha());
    return ResponseEntity.ok(new LoginResponse(token));
  }
}