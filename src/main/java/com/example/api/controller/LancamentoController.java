package com.example.api.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.repository.dto.LancamentoDTO;
import com.example.api.repository.filter.LancamentoFilter;
import com.example.api.service.LancamentoService;

/**
 * Controlador referente aos dados de lançamento.
 */
@RestController
@RequestMapping("/lancamentos")
public class LancamentoController {

	@Autowired
	private LancamentoService lancamentoService;

	/**
	 * Retorna o lancamentoDTO paginado.
	 *
	 * @param lancamentoFilter
	 * @param pageable
	 * @return Page<LancamentoDTO>
	 */
	@GetMapping(value = "/filtro/paginacao")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
	public Page<LancamentoDTO> pesquisarDTOPaginado(LancamentoFilter lancamentoFilter, Pageable pageable) {
		return lancamentoService.pesquisarDTOPaginado(lancamentoFilter, pageable);
	}

	/**
	 * Retorna os lançamentos conforme o filtro informado.
	 *
	 * @param lancamentoFilter
	 * @return ResponseEntity<List<LancamentoDTO>>
	 */
	@GetMapping(value = "/filtro")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
	public ResponseEntity<List<LancamentoDTO>> consultarPorFiltro(final LancamentoFilter lancamentoFilter) {
		List<LancamentoDTO> lancamentos = lancamentoService.getLancamentosPorFiltro(lancamentoFilter);

		return ResponseEntity.ok(lancamentos);
	}

	/**
	 * Busca um lançamento por seu código.
	 *
	 * @param codigo
	 * @return ResponseEntity<Lancamento>
	 */
	@GetMapping(value = "/{codigo}")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
	public ResponseEntity<LancamentoDTO> consultar(@PathVariable final Long codigo) {
		LancamentoDTO lancamento = lancamentoService.consultarPorCodigo(codigo);

		return lancamento != null ? ResponseEntity.ok(lancamento) : ResponseEntity.notFound().build();
	}

	/**
	 * Cria um lançamento.
	 *
	 * @param lancamento
	 * @param response
	 * @return ResponseEntity<Lancamento>
	 */
	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_LANCAMENTO') and #oauth2.hasScope('write')")
	public ResponseEntity<LancamentoDTO> salvar(@Valid @RequestBody final LancamentoDTO lancamento,
			final HttpServletResponse response) {
		LancamentoDTO lancamentoSalvo = lancamentoService.salvar(lancamento);

		return ResponseEntity.status(HttpStatus.CREATED).body(lancamentoSalvo);
	}

	/**
	 * Remove um lançamento.
	 *
	 * @param codigo
	 */
	@DeleteMapping(value = "/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ROLE_REMOVER_LANCAMENTO') and #oauth2.hasScope('write')")
	public void excluir(@PathVariable Long codigo) {
		lancamentoService.excluir(codigo);
	}

	/**
	 * Altera o lançamento informado.
	 *
	 * @param codigo
	 * @param lancamento
	 * @return ResponseEntity<Lancamento>
	 */
	@PutMapping(value = "/{codigo}")
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_LANCAMENTO')")
	public ResponseEntity<LancamentoDTO> alterar(@PathVariable final Long codigo,
			final @RequestBody LancamentoDTO lancamento) {
		LancamentoDTO lancamentoAlterado = lancamentoService.alterar(codigo, lancamento);

		return ResponseEntity.ok(lancamentoAlterado);
	}

}
