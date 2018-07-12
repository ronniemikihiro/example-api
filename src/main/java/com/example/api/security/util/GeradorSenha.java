package com.example.api.security.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Gera uma senha encodada. (Utilizada para testes, não faz parte do projeto).
 */
public class GeradorSenha {

	public static void main(String[] args) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		System.out.println(encoder.encode("admin"));
	}

}
