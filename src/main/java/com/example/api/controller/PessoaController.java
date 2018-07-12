package com.example.api.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.example.api.model.Categoria;
import com.example.api.model.Pessoa;
import com.example.api.repository.CategoriaRepository;
import com.example.api.repository.PessoaRepository;
import com.example.api.repository.dto.LancamentoDTO;
import com.example.api.repository.filter.LancamentoFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.repository.dto.PessoaDTO;
import com.example.api.service.PessoaService;

/**
 * Controlador referente aos dados de pessoa.
 */
@RestController
@RequestMapping("/pessoas")
public class PessoaController {

	@Autowired
	private PessoaService pessoaService;

	@Autowired
	private PessoaRepository pessoaRepository;

	/**
	 * Retorna o lancamentoDTO paginado.
	 *
	 * @param pageable
	 * @return Page<PessoaDTO>
	 */
	@GetMapping(value = "/filtro/paginacao")
	public Page<Pessoa> findByNomeContaining(final String nome, final Pageable pageable) {
		return pessoaService.findByNomeContaining(nome, pageable);
	}

	/**
	 * Retorna a lista de categorias.
	 *
	 * @return List<Pessoa>
	 */
	@GetMapping(value = "/listar")
	public List<Pessoa> listar() {
		return pessoaRepository.findAll();
	}

	/**
	 * Cria uma Pessoa.
	 *
	 * @param pessoa
	 * @param response
	 * @return ResponseEntity<Pessoa>
	 */
	@PostMapping
	public ResponseEntity<PessoaDTO> salvar(@RequestBody final PessoaDTO pessoa, final HttpServletResponse response) {
		final PessoaDTO pessoaSalva = pessoaService.salvar(pessoa);
		return ResponseEntity.status(HttpStatus.CREATED).body(pessoaSalva);
	}

	/**
	 * Busca uma pessoa por seu código.
	 *
	 * @param codigo
	 * @return ResponseEntity<Pessoa>
	 */
	@GetMapping(value = "/{codigo}")
	public ResponseEntity<PessoaDTO> buscarPorCodigo(@PathVariable final Long codigo) {
		final PessoaDTO pessoa = pessoaService.consultarPorCodigo(codigo);
		return pessoa != null ? ResponseEntity.ok(pessoa) : ResponseEntity.notFound().build();
	}

	/**
	 * Busca uma pessoa por seu código.
	 *
	 * @return ResponseEntity<Pessoa>
	 */
	@GetMapping
	public ResponseEntity<List<PessoaDTO>> listarTodos() {
		List<PessoaDTO> pessoas = pessoaService.listarTodos();

		return ResponseEntity.ok(pessoas);
	}

	
	/**
	 * Exclui uma pessoa por seu código.
	 *
	 * @param codigo
	 */
	@DeleteMapping(value = "/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void excluir(@PathVariable final Long codigo) {
		pessoaService.excluir(codigo);
	}

	/**
	 * Atualiza uma Pessoa.
	 *
	 * @param codigo
	 * @param pessoa
	 * @return ResponseEntity<Pessoa>
	 */
	@PutMapping(value = "/{codigo}")
	public ResponseEntity<PessoaDTO> atualizar(@PathVariable final Long codigo,
			@Valid @RequestBody final PessoaDTO pessoa) {
		final PessoaDTO pessoaSalva = pessoaService.atualizar(codigo, pessoa);

		return ResponseEntity.ok(pessoaSalva);
	}

	/**
	 * Atualiza uma pessoa para propriedade ativo.
	 *
	 * @param codigo
	 */
	@PutMapping(value = "/{codigo}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void ativar(final @PathVariable Long codigo) {
		pessoaService.ativar(codigo);
	}

	/**
	 * Atualiza uma pessoa para propriedade ativo.
	 *
	 * @param codigo
	 * @param ativo
	 */
	@PutMapping(value = "/{codigo}/inativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void inativar(final @PathVariable Long codigo) {
		pessoaService.inativar(codigo);
	}

	/**
	 * Pesquisa uma pessoa paginada.
	 *
	 * @param nome
	 * @param pageable
	 * @return Page<Pessoa>
	 */
	@GetMapping("/nome")
	public Page<PessoaDTO> listar(@RequestParam(required = false, defaultValue = "%") final String nome,
			final Pageable pageable) {
		return pessoaService.consultarPorNome(nome, pageable);
	}

}
