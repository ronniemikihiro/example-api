package com.example.api.repository.lancamento;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.api.repository.dto.LancamentoDTO;
import com.example.api.repository.filter.LancamentoFilter;

/**
 * Interface que representa os métodos de querys criadas.
 */
public interface LancamentoRepositoryQuery {


	Page<LancamentoDTO> pesquisarDTOPaginado(final LancamentoFilter lancamentoFilter, final Pageable pageable);

	/**
	 * Retorna os lançamentos conforme o filtro informado.
	 * 
	 * @param lancamentoFilter
	 * @return
	 */
	List<LancamentoDTO> getLancamentosPorFiltro(final LancamentoFilter lancamentoFilter);

	/**
	 * Retorna o lançamento conforme o código informado.
	 * 
	 * @return
	 */
	LancamentoDTO consultarPorCodigo(final Long codigo);

}
