package com.example.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.example.api.config.property.ExampleApiProperty;

/**
 * Classe principal de inicialização do projeto.
 */
@SpringBootApplication
@EnableConfigurationProperties(ExampleApiProperty.class)
public class ExampleApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExampleApiApplication.class, args);
	}
	
}
