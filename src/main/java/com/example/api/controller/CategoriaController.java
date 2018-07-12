package com.example.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.model.Categoria;
import com.example.api.repository.CategoriaRepository;

/**
 * Controller que manipula as categorias.
 */
@RestController
@RequestMapping("/categorias")
public class CategoriaController {

	@Autowired
	private CategoriaRepository categoriaRepository;

	/**
	 * Retorna a lista de categorias.
	 *
	 * @return List<Categoria>
	 */
	@GetMapping
	public List<Categoria> listar() {
		return categoriaRepository.findAll();
	}

}
