package me.wendersonfarias.empregapi;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EmpregapiApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(EmpregapiApplication.class, args);
	}

	@Override
	public void run(String... args) {

		System.out.println("Iniciando aplicação...");
	}
}
