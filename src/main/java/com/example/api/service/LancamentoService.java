package com.example.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.example.api.constantes.ConstanteMensagem;
import com.example.api.exception.NegocioException;
import com.example.api.model.Categoria;
import com.example.api.model.Lancamento;
import com.example.api.model.Pessoa;
import com.example.api.repository.LancamentoRepository;
import com.example.api.repository.PessoaRepository;
import com.example.api.repository.dto.LancamentoDTO;
import com.example.api.repository.filter.LancamentoFilter;

/**
 * Serviço responsável pela implementação das regras de negócio referentes à
 * Lançamentos.
 */
@Service
public class LancamentoService {

	@Autowired
	private PessoaRepository pessoaRepository;

	@Autowired
	private LancamentoRepository lancamentoRepository;

	/**
	 * Salva e valida um lançamento.
	 *
	 * @param lancamentoDTO
	 * @return Lancamento
	 */
	public LancamentoDTO salvar(final LancamentoDTO lancamentoDTO) {
		validarPessoa(lancamentoDTO);

		if (lancamentoDTO.getCodigo() == null) {
			inserir(lancamentoDTO);
		} else {
			alterar(lancamentoDTO);
		}

		return lancamentoDTO;
	}

	/**
	 * Insere um novo lançamento.
	 * 
	 * @param lancamentoDTO
	 */
	private void inserir(final LancamentoDTO lancamentoDTO) {
		Lancamento lancamento = Lancamento.newInstance(lancamentoDTO);
		lancamentoRepository.save(lancamento);
		lancamentoDTO.setCodigo(lancamento.getCodigo());
	}

	/**
	 * Altera o lancamento informado.
	 * 
	 * @param lancamentoDTO
	 */
	private void alterar(final LancamentoDTO lancamentoDTO) {
		Lancamento lancamento = atualizarDados(lancamentoDTO);
		lancamentoRepository.save(lancamento);
	}

	/**
	 * Rertorna o Lancamento atualizado conforme os dados do DTO informado.
	 * 
	 * @param lancamentoDTO
	 * @return
	 */
	private Lancamento atualizarDados(final LancamentoDTO lancamentoDTO) {
		Lancamento lancamento = consultarLancamentoExistente(lancamentoDTO.getCodigo());
		Categoria categoria = Categoria.newInstante(lancamentoDTO.getCategoria());
		lancamento.setCategoria(categoria);
		Pessoa pessoa = Pessoa.newInstante(lancamentoDTO.getPessoa());
		lancamento.setPessoa(pessoa);
		lancamento.setDataPagamento(lancamentoDTO.getDataPagamento());
		lancamento.setObservacao(lancamentoDTO.getObservacao());
		lancamento.setTipo(lancamentoDTO.getTipo());
		lancamento.setValor(lancamentoDTO.getValor());

		return lancamento;
	}

	/**
	 * Atualiza e salvar um lançamento.
	 *
	 * @param codigo
	 * @param lancamentoDTO
	 * @return Lancamento
	 */
	public LancamentoDTO alterar(final Long codigo, final LancamentoDTO lancamentoDTO) {
		final Lancamento lancamento = consultarLancamentoExistente(codigo);

		if (!lancamentoDTO.getPessoa().equals(lancamento.getPessoa())) {
			validarPessoa(lancamentoDTO);
		}

		Lancamento lancamentoAtualizado = atualizarDados(lancamentoDTO);

		lancamentoRepository.save(lancamentoAtualizado);

		return lancamentoDTO;
	}

	/**
	 * Valida uma pessoa de acordo com seu código.
	 *
	 * @param lancamento
	 */
	private void validarPessoa(final LancamentoDTO lancamento) {
		if (lancamento == null) {
			throw new IllegalArgumentException();
		}

		Pessoa pessoa = null;

		if (lancamento.getPessoa() != null) {
			pessoa = pessoaRepository.findOne(lancamento.getPessoa().getCodigo());
		}

		if (ObjectUtils.isEmpty(pessoa) || pessoa.isInativo()) {
			throw new NegocioException(ConstanteMensagem.MSG_PESSOA_INEXISTENTE_INATIVA);
		}
	}

	/**
	 * Busca um lançamento existente.
	 *
	 * @param codigo
	 * @return Lancamento
	 */
	private Lancamento consultarLancamentoExistente(final Long codigo) {
		if (codigo == null) {
			throw new IllegalArgumentException();
		}

		final Lancamento lancamentoSalvo = lancamentoRepository.findOne(codigo);

		if (lancamentoSalvo == null) {
			throw new NegocioException(ConstanteMensagem.MSG_LANCAMENTO_NAO_ENCONTRADO);
		}

		return lancamentoSalvo;
	}

	/**
	 * Retorna o lançamento conforme o código informado.
	 * 
	 * @return
	 */
	public LancamentoDTO consultarPorCodigo(final Long codigo) {
		if (codigo == null) {
			throw new IllegalArgumentException();
		}

		return lancamentoRepository.consultarPorCodigo(codigo);
	}

	/**
	 * Exclui o lançamento informado.
	 * 
	 * @param codigo
	 */
	public void excluir(final Long codigo) {
		if (codigo == null) {
			throw new IllegalArgumentException();
		}

		lancamentoRepository.delete(codigo);
	}

	/**
	 * @param lancamentoFilter
	 * @param pageable
	 * @return
	 */
	public Page<LancamentoDTO> pesquisarDTOPaginado(LancamentoFilter lancamentoFilter, Pageable pageable) {
		return lancamentoRepository.pesquisarDTOPaginado(lancamentoFilter, pageable);
	}

	/**
	 * Retorna os lançamentos conforme o filtro informado.
	 * 
	 * @param lancamentoFilter
	 * @return
	 */
	public List<LancamentoDTO> getLancamentosPorFiltro(final LancamentoFilter lancamentoFilter) {
		if (lancamentoFilter == null) {
			throw new IllegalArgumentException();
		}

		return lancamentoRepository.getLancamentosPorFiltro(lancamentoFilter);
	}

}
