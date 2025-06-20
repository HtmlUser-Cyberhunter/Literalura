package com.aluracursos.Litarelura;


import com.aluracursos.Litarelura.principal.Principal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class LitareluraApplication {

	public static void main(String[] args) {
		SpringApplication.run(LitareluraApplication.class, args);
	}
	@Bean
	public CommandLineRunner ejecutar(Principal principal) {
		return args -> principal.muestraElMenu();
	}
}
