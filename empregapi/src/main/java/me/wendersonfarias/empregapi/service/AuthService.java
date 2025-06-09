package me.wendersonfarias.empregapi.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import me.wendersonfarias.empregapi.security.JwtService;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final AuthenticationManager authenticationManager;
  private final JwtService jwtService;

  public String authenticate(String email, String senha) {

    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(email, senha);

    Authentication authentication = authenticationManager.authenticate(authToken);

    UserDetails userDetails = (UserDetails) authentication.getPrincipal();

    return jwtService.generateToken(userDetails);
  }
}