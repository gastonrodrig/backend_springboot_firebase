package com.gato.multi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MultiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MultiApplication.class, args);
		System.out.println("\n========================================");
		System.out.println("Aplicación iniciada. Accede a Swagger UI en:");
		System.out.println("http://localhost:8080/swagger-ui/index.html#");
		System.out.println("========================================");
	}

}
