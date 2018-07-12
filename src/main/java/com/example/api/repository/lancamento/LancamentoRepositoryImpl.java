package com.example.api.repository.lancamento;

import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import com.example.api.repository.dto.LancamentoDTO;
import com.example.api.repository.filter.LancamentoFilter;

/**
 * Implementação do do repositório do Lançamento.
 */
public class LancamentoRepositoryImpl implements LancamentoRepositoryQuery {

	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page<LancamentoDTO> pesquisarDTOPaginado(final LancamentoFilter lancamentoFilter, final Pageable pageable) {
		StringBuilder jpql = new StringBuilder();
		HashMap<String, Object> parametros = new HashMap<>();

		jpql.append(" select new com.example.api.repository.dto.LancamentoDTO(lancamento.codigo, ");
		jpql.append(" lancamento.descricao, lancamento.dataVencimento, lancamento.observacao, ");
		jpql.append(" lancamento.dataPagamento, lancamento.valor, lancamento.tipo, categoria.nome, ");
		jpql.append(" pessoa.nome, pessoa.codigo, categoria.codigo ) ");
		jpql.append(" from Lancamento lancamento ");
		jpql.append(" inner join lancamento.categoria categoria ");
		jpql.append(" inner join lancamento.pessoa pessoa ");
		jpql.append(" where 1=1 ");

		adicionarFiltrosPesquisa(lancamentoFilter, jpql, parametros);

		jpql.append(" order by lancamento.dataVencimento ");

		TypedQuery<LancamentoDTO> query = entityManager.createQuery(jpql.toString(), LancamentoDTO.class);
		adicionarFiltroPaginacao(query, pageable);
		parametros.entrySet().stream().forEach(param -> query.setParameter(param.getKey(), param.getValue()));

		return new PageImpl<>(query.getResultList(), pageable, consultarTotalPorFiltro(lancamentoFilter));
	}

	/**
	 * Retorna o total de registros conforme o filtro informado.
	 *
	 * @param lancamentoFilter
	 * @return Long
	 */
	private Long consultarTotalPorFiltro(final LancamentoFilter lancamentoFilter) {
		StringBuilder jpql = new StringBuilder();
		HashMap<String, Object> params = new HashMap<>();

		jpql.append(" select count(lancamento.id) from Lancamento lancamento ");
		jpql.append(" where 1=1 ");

		adicionarFiltrosPesquisa(lancamentoFilter, jpql, params);

		TypedQuery<Long> query = entityManager.createQuery(jpql.toString(), Long.class);
		params.entrySet().stream().forEach(param -> query.setParameter(param.getKey(), param.getValue()));
		return query.getSingleResult();
	}

	/**
	 * Adiciona os Filtros de Pesquisa do Lancamento.
	 *
	 * @param lancamentoFilter
	 * @param jpql
	 * @param params
	 */
	private void adicionarFiltrosPesquisa(final LancamentoFilter lancamentoFilter, final StringBuilder jpql,
			final HashMap<String, Object> params) {

		if (!StringUtils.isEmpty(lancamentoFilter.getDescricao())) {
			jpql.append(" and lower(lancamento.descricao) like :descricao ");
			params.put("descricao", "%" + lancamentoFilter.getDescricao().toLowerCase() + "%");
		}

		if (lancamentoFilter.getDataVencimentoDe() != null) {
			jpql.append(" and lancamento.dataVencimento >= :dataVencimentoDe ");
			params.put("dataVencimentoDe", lancamentoFilter.getDataVencimentoDe());
		}

		if (lancamentoFilter.getDataVencimentoAte() != null) {
			jpql.append(" and lancamento.dataVencimento <= :dataVencimentoAte ");
			params.put("dataVencimentoAte", lancamentoFilter.getDataVencimentoAte());
		}
	}

	/**
	 * Adiciona os filtros de paginação.
	 *
	 * @param query
	 * @param pageable
	 */
	private void adicionarFiltroPaginacao(final TypedQuery<?> query, final Pageable pageable) {
		final int paginaAtual = pageable.getPageNumber();
		final int totalRegistrosPorPagina = pageable.getPageSize();
		final int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;

		query.setFirstResult(primeiroRegistroDaPagina);
		query.setMaxResults(totalRegistrosPorPagina);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<LancamentoDTO> getLancamentosPorFiltro(final LancamentoFilter lancamentoFilter) {
		StringBuilder jpql = new StringBuilder();
		HashMap<String, Object> parametros = new HashMap<>();

		jpql.append(" select new com.example.api.repository.dto.LancamentoDTO(lancamento.codigo, ");
		jpql.append(" lancamento.descricao, lancamento.dataVencimento, lancamento.observacao, ");
		jpql.append(" lancamento.dataPagamento, lancamento.valor, lancamento.tipo, categoria.nome, ");
		jpql.append(" pessoa.nome, pessoa.codigo, categoria.codigo) ");
		jpql.append(" from Lancamento lancamento ");
		jpql.append(" inner join lancamento.categoria categoria ");
		jpql.append(" inner join lancamento.pessoa pessoa ");
		jpql.append(" where 1=1 ");

		adicionarFiltrosPesquisa(lancamentoFilter, jpql, parametros);

		jpql.append(" order by lancamento.dataVencimento ");

		final TypedQuery<LancamentoDTO> query = entityManager.createQuery(jpql.toString(), LancamentoDTO.class);
		parametros.entrySet().stream().forEach(param -> query.setParameter(param.getKey(), param.getValue()));

		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public LancamentoDTO consultarPorCodigo(final Long codigo) {
		StringBuilder jpql = new StringBuilder();

		jpql.append(" select new com.example.api.repository.dto.LancamentoDTO(lancamento.codigo, ");
		jpql.append(" lancamento.descricao, lancamento.dataVencimento, lancamento.observacao, ");
		jpql.append(" lancamento.dataPagamento, lancamento.valor, lancamento.tipo, categoria.nome, pessoa.nome, ");
		jpql.append(" pessoa.codigo, categoria.codigo) ");
		jpql.append(" from Lancamento lancamento ");
		jpql.append(" left join lancamento.categoria categoria ");
		jpql.append(" inner join lancamento.pessoa pessoa ");
		jpql.append(" where lancamento.codigo= :codigo ");

		TypedQuery<LancamentoDTO> query = entityManager.createQuery(jpql.toString(), LancamentoDTO.class);
		query.setParameter("codigo", codigo);

		return query.getSingleResult();
	}

}
