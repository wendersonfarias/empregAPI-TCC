package me.wendersonfarias.empregapi.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  // Suas dependências injetadas
  private final JwtAuthenticationFilter jwtAuthFilter;
  private final UserDetailsService userDetailsService;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(auth -> auth
            // ENDPOINTS PÚBLICOS
            .requestMatchers("/").permitAll()
            .requestMatchers(
                "/api/auth/**", // Libera /api/auth/login e futuros endpoints de autenticação
                "/v3/api-docs/**", // Libera a documentação OpenAPI em formato JSON
                "/swagger-ui.html", // Libera a página principal do Swagger
                "/swagger-ui/**", // Libera os recursos da interface web do Swagger
                "/swagger-resources/**",
                "/webjars/**")
            .permitAll()

            // Endpoints de cadastro continuam públicos
            .requestMatchers(HttpMethod.POST, "/api/candidato").permitAll()
            .requestMatchers(HttpMethod.POST, "/api/empresa").permitAll()
            .requestMatchers(HttpMethod.GET, "api/vagas").permitAll()

            // ENDPOINTS PROTEGIDOS
            .anyRequest().authenticated())
        // Configuração para API Stateless com JWT
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authenticationProvider(authenticationProvider())
        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

  @Bean
  public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(userDetailsService);
    authProvider.setPasswordEncoder(passwordEncoder());
    return authProvider;
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
    return config.getAuthenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}