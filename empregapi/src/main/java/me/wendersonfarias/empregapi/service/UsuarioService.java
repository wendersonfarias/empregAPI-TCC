package me.wendersonfarias.empregapi.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import me.wendersonfarias.empregapi.enumeracao.Role;
import me.wendersonfarias.empregapi.exception.EmailJaCadastradoException;
import me.wendersonfarias.empregapi.model.Usuario;
import me.wendersonfarias.empregapi.repository.UsuarioRepository;

@Service
@RequiredArgsConstructor
public class UsuarioService {

  private final UsuarioRepository usuarioRepository;
  private final PasswordEncoder passwordEncoder;

  public Usuario criarUsuario(String email, String senha, Role role) {

    usuarioRepository.findByEmail(email).ifPresent(user -> {
      throw new EmailJaCadastradoException("Este e-mail já está em uso: " + email);
    });

    Usuario novoUsuario = new Usuario();
    novoUsuario.setEmail(email);
    novoUsuario.setSenha(passwordEncoder.encode(senha));
    novoUsuario.setRole(role);

    return usuarioRepository.save(novoUsuario);
  }
}