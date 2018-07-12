package com.example.api.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.example.api.repository.dto.CategoriaDTO;

/**
 * Implementação do repositório customizado para os dados de Categoria.
 */
public class CategoriaRepositoryImpl implements CategoriaRepositoryQuery {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<CategoriaDTO> listarTodos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CategoriaDTO consultarPorCodigo(Long codigo) {
		// TODO Auto-generated method stub
		return null;
	}

}
