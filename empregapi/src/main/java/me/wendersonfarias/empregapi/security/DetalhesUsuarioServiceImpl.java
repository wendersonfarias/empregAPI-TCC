package me.wendersonfarias.empregapi.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service; // <-- A ANOTAÇÃO ESSENCIAL

import lombok.RequiredArgsConstructor;
import me.wendersonfarias.empregapi.model.Usuario;
import me.wendersonfarias.empregapi.repository.UsuarioRepository;

@Service
@RequiredArgsConstructor
public class DetalhesUsuarioServiceImpl implements UserDetailsService {

  private final UsuarioRepository usuarioRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Usuario usuario = usuarioRepository.findByEmail(username)
        .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o e-mail: " + username));

    return User.builder()
        .username(usuario.getEmail())
        .password(usuario.getSenha())
        .roles(usuario.getRole().name().replace("ROLE_", ""))
        .build();
  }
}