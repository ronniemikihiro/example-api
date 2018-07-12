package com.example.api.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.api.repository.dto.PessoaDTO;

/**
 * Definição das consultas customizadas referentes aos dados de Pessoa.
 */
public interface PessoaRepositoryQuery {

	/**
	 * Retorna os dados de pessoa sobre demanda conforme o nome informado.
	 * 
	 * @param nome
	 * @param pageable
	 * @return
	 */
	public Page<PessoaDTO> consultarPorNome(final String nome, Pageable pageable);

	/**
	 * Retorna todas as pessoas;
	 * 
	 * @return
	 */
	public List<PessoaDTO> listarTodos();

	/**
	 * Retorna o lançamento conforme o código informado.
	 * 
	 * @return
	 */
	public PessoaDTO consultarPorCodigo(final Long codigo);

}
