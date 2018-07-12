package com.example.api.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.example.api.constantes.ConstanteMensagem;
import com.example.api.exception.NegocioException;
import com.example.api.model.Pessoa;
import com.example.api.repository.PessoaRepository;
import com.example.api.repository.dto.PessoaDTO;

/**
 * Serviço responsável pela implementação das regras de negócio relacionadas á
 * Pessoa.
 */
@Service
public class PessoaService {

	@Autowired
	private PessoaRepository pessoaRepository;

	/**
	 * Retorna as pessoas por nome paginado.
	 *
	 * @param pageable
	 * @return Page<LancamentoDTO>
	 */
	public Page<Pessoa> findByNomeContaining(final String nome, final Pageable pageable) {
		return pessoaRepository.findByNomeContaining(nome == null ? "" : nome, pageable);
	}

	/**
	 * Retorna todas as pessoas cadastradas.
	 * 
	 * @return
	 */
	public List<PessoaDTO> listarTodos() {
		return pessoaRepository.listarTodos();
	}

	/**
	 * Atualiza uma pessoa.
	 * 
	 * @param codigo
	 * @param pessoa
	 * @return
	 */
	@Transactional
	public PessoaDTO atualizar(final Long codigo, final PessoaDTO pessoa) {
		if (codigo == null || pessoa == null) {
			throw new IllegalArgumentException();
		}

		alterar(pessoa);

		return pessoa;
	}

	/**
	 * Ativa a pessoa referente ao código informado.
	 *
	 * @param codigo
	 */
	public void ativar(final Long codigo) {
		if (codigo == null) {
			throw new IllegalArgumentException();
		}

		Pessoa pessoaSalva = pessoaRepository.findOne(codigo);
		pessoaSalva.setAtivo(Boolean.TRUE);
		pessoaRepository.save(pessoaSalva);
	}

	/**
	 * Inativa a pessoa referente ao código informado.
	 *
	 * @param codigo
	 */
	public void inativar(final Long codigo) {
		if (codigo == null) {
			throw new IllegalArgumentException();
		}

		Pessoa pessoaSalva = pessoaRepository.findOne(codigo);
		pessoaSalva.setAtivo(Boolean.FALSE);
		pessoaRepository.save(pessoaSalva);
	}

	/**
	 * Retorna a pessoa conforme o código informado.
	 *
	 * @param codigo
	 * @return Pessoa
	 */
	public PessoaDTO consultarPorCodigo(final Long codigo) {
		final PessoaDTO pessoaSalva = pessoaRepository.consultarPorCodigo(codigo);

		if (ObjectUtils.isEmpty(pessoaSalva)) {
			throw new NegocioException(ConstanteMensagem.MSG_NENHUM_REGISTRO_ENCONTRADO);
		}

		return pessoaSalva;
	}

	/**
	 * Salva a {@link Pessoa} conforme os dados informados.
	 * 
	 * @param pessoa
	 * @return
	 */
	@Transactional
	public PessoaDTO salvar(PessoaDTO pessoa) {
		if (pessoa == null) {
			throw new IllegalArgumentException();
		}

		if (pessoa.getCodigo() == null) {
			inserir(pessoa);
		} else {
			alterar(pessoa);
		}

		return pessoa;
	}

	/**
	 * Insere uma nova pessoa conforme o DTO informado.
	 * 
	 * @param pessoa
	 */
	private void inserir(PessoaDTO pessoa) {
		Pessoa novaPessoa = Pessoa.newInstante(pessoa);
		novaPessoa.setAtivo(Boolean.TRUE);
		pessoaRepository.save(novaPessoa);
		pessoa.setCodigo(novaPessoa.getCodigo());
	}

	/**
	 * Altera os dados da pessoa conforme os dados do DTO informado.
	 * 
	 * @param pessoaDTO
	 */
	private void alterar(PessoaDTO pessoaDTO) {
		Pessoa pessoa = pessoaRepository.findOne(pessoaDTO.getCodigo());

		pessoa.setAtivo(pessoaDTO.getAtivo());
		pessoa.setNome(pessoaDTO.getNome());
		pessoa.setEndereco(pessoaDTO.getEndereco());

		pessoaRepository.save(pessoa);
	}

	/**
	 * Exclui a pessoa conforme o código informado.
	 * 
	 * @param codigo
	 */
	@Transactional
	public void excluir(final Long codigo) {
		if (codigo == null) {
			throw new IllegalArgumentException();
		}

		pessoaRepository.delete(codigo);
	}

	/**
	 * Retorna as pessoas conforme o nome informado.
	 * 
	 * @param nome
	 * @param pageable
	 * @return
	 */
	public Page<PessoaDTO> consultarPorNome(final String nome, Pageable pageable) {
		return pessoaRepository.consultarPorNome(nome, pageable);
	}

}
