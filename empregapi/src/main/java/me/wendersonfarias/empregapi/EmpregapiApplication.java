package me.wendersonfarias.empregapi;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import me.wendersonfarias.empregapi.dto.CandidatoRequest;
import me.wendersonfarias.empregapi.dto.CandidatoResponse;
import me.wendersonfarias.empregapi.dto.EmpresaRequest;
import me.wendersonfarias.empregapi.dto.EmpresaResponse;
import me.wendersonfarias.empregapi.service.CandidatoService;
import me.wendersonfarias.empregapi.service.EmpresaService;

@SpringBootApplication
public class EmpregapiApplication implements CommandLineRunner {

	@Autowired
	private CandidatoService candidatoService;

	@Autowired
	private EmpresaService empresaService;

	public static void main(String[] args) {
		SpringApplication.run(EmpregapiApplication.class, args);
	}

	@Override
	public void run(String... args) {

		CandidatoRequest candidato = new CandidatoRequest();

		candidato.setNomeCompleto("Wenderson Farias");
		candidato.setEmail("wenderson@email.com");
		candidato.setTelefone("123456789");
		candidato.setEndereco("Rua Exemplo, 123");
		candidato.setExperienciaProfissional("5 anos em desenvolvimento Java");
		candidato.setHabilidades("Java, Spring Boot, SQL");
		candidato.setHabilidades("ler e escrever");
		candidato.setDataNascimento(LocalDate.of(1990, 1, 1));
		candidato.setEscolaridade("Ensino Superior Completo");
		candidato.setSenha("senhaSegura123");

		// Salva o candidato no banco de dados
		CandidatoResponse candidatoResponse = candidatoService.salvarCandidato(candidato);

		// Exibe os dados do candidato salvo
		System.out.println(candidatoResponse.toString());

		EmpresaRequest empresa = new EmpresaRequest();
		empresa.setNome("Empresa Exemplo");
		empresa.setCnpj("27865757000102");
		empresa.setEmail("empresa@email.com");
		empresa.setSenha("senhaSegura123");
		empresa.setWebsite("https://www.empresaexemplo.com");
		empresa.setDescription("Empresa de tecnologia especializada em desenvolvimento de software.");

		// Salva a empresa no banco de dados
		EmpresaResponse empresaResponse = empresaService.create(empresa);
		// Exibe os dados da empresa salva
		System.out.println(empresaResponse.toString());

	}
}
