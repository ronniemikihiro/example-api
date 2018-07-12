package com.example.api.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.example.api.repository.dto.PessoaDTO;

/**
 * Implementação do repositório customizado para os dados de Lançamento.
 */
public class PessoaRepositoryImpl implements PessoaRepositoryQuery {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Page<PessoaDTO> consultarPorNome(String nome, Pageable pageable) {
		StringBuilder jpql = new StringBuilder();

		jpql.append(" select new com.example.api.repository.dto.PessoaDTO(pessoa.codigo,  ");
		jpql.append("pessoa.nome, pessoa.ativo, pessoa.endereco ) ");
		jpql.append(" from Pessoa pessoa ");
		jpql.append(" where lower(pessoa.nome) like :nome ");

		final TypedQuery<PessoaDTO> query = entityManager.createQuery(jpql.toString(), PessoaDTO.class);
		query.setParameter("nome", "%" + nome.toLowerCase() + "%");

		return new PageImpl<>(query.getResultList(), pageable, count(nome));
	}

	
	@Override
	public List<PessoaDTO> listarTodos() {
		StringBuilder jpql = new StringBuilder();

		jpql.append(" select new com.example.api.repository.dto.PessoaDTO(pessoa.codigo,  ");
		jpql.append("pessoa.nome, pessoa.ativo, pessoa.endereco ) ");
		jpql.append(" from Pessoa pessoa ");

		TypedQuery<PessoaDTO> query = entityManager.createQuery(jpql.toString(), PessoaDTO.class);

		return query.getResultList();
	}

	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public PessoaDTO consultarPorCodigo(final Long codigo) {
		StringBuilder jpql = new StringBuilder();

		jpql.append(" select new com.example.api.repository.dto.PessoaDTO(pessoa.codigo,  ");
		jpql.append("pessoa.nome, pessoa.ativo, pessoa.endereco ) ");
		jpql.append(" from Pessoa pessoa ");
		jpql.append(" where pessoa.codigo = :codigo");

		final TypedQuery<PessoaDTO> query = entityManager.createQuery(jpql.toString(), PessoaDTO.class);
		query.setParameter("codigo", codigo);

		return query.getSingleResult();

	}

	/**
	 * Retorna o total de registros conforme o nome informado.
	 * 
	 * @param nome
	 * @return
	 */
	private Long count(final String nome) {
		final StringBuilder jpql = new StringBuilder();

		jpql.append(" select count(pessoa.id) from Pessoa pessoa ");
		jpql.append(" where lower(pessoa.nome) like :nome ");

		TypedQuery<Long> query = entityManager.createQuery(jpql.toString(), Long.class);
		query.setParameter("nome", "%" + nome.toLowerCase() + "%");

		return query.getSingleResult();
	}

}
