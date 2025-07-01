package me.wendersonfarias.empregapi.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import lombok.RequiredArgsConstructor;
import me.wendersonfarias.empregapi.enumeracao.Role;
import me.wendersonfarias.empregapi.repository.UsuarioRepository;
import me.wendersonfarias.empregapi.service.UsuarioService;

@Configuration
@Profile("test")
@RequiredArgsConstructor
public class TestConfiguracao implements CommandLineRunner {

  private final UsuarioRepository usuarioRepository;
  private final UsuarioService usuarioService;

  @Override
  public void run(String... args) throws Exception {

    if (usuarioRepository.findByEmail("admin@empregapi.com").isEmpty()) {
      System.out.println("Criando usuário ADMINISTRADOR padrão...");
      usuarioService.criarUsuario("admin@empregapi.com", "admin123", Role.ROLE_ADMIN);
      System.out.println("Usuário ADMINISTRADOR criado com sucesso!");
    } else {
      System.out.println("Usuário ADMINISTRADOR já existe.");
    }
  }
}