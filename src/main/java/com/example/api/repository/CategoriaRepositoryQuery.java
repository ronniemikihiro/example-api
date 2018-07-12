package com.example.api.repository;

import java.util.List;

import com.example.api.repository.dto.CategoriaDTO;

/**
 * Definição das consultas customizadas referentes aos dados de Categoria.
 */
public interface CategoriaRepositoryQuery {

	/**
	 * Retorna todas as categorias.
	 * 
	 * @return
	 */
	public List<CategoriaDTO> listarTodos();

	/**
	 * Retorna o lançamento conforme o código informado.
	 * 
	 * @return
	 */
	public CategoriaDTO consultarPorCodigo(final Long codigo);

}
