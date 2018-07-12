package com.example.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.api.model.Pessoa;

/**
 * Repositório de acesso aos dados de pessoa.
 *
 */
public interface PessoaRepository extends JpaRepository<Pessoa, Long>,  PessoaRepositoryQuery  {

	/**
	 * Retorna os dados de pessoa sobre demanda.
	 * 
	 * @param nome
	 * @param pageable
	 * @return
	 */
	Page<Pessoa> findByNomeContaining(String nome, Pageable pageable);

}
