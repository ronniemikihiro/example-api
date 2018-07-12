package com.example.api.repository.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.example.api.model.TipoLancamento;

/**
 * Classe de transfêrencia com as informações básicas de lançamento.
 */
public class LancamentoDTO {

	private Long codigo;
	private String descricao;
	private LocalDate dataVencimento;
	private LocalDate dataPagamento;
	private BigDecimal valor;
	private TipoLancamento tipo;
	private String nomeCategoria;
	private String nomePessoa;
	private Long codigoCategoria;
	private Long codigoPessoa;
	private String observacao;
	private PessoaDTO pessoa;
	private CategoriaDTO categoria;

	/**
	 * Construtor padrão.
	 */
	public LancamentoDTO() {

	}

	/**
	 * Cnstrutor da classe.
	 * 
	 * @param codigo
	 * @param descricao
	 * @param dataVencimento
	 * @param dataPagamento
	 * @param valor
	 * @param tipo
	 * @param nomeCategoria
	 * @param nomePessoa
	 */
	public LancamentoDTO(Long codigo, String descricao, LocalDate dataVencimento, String observacao, LocalDate dataPagamento,
			BigDecimal valor, TipoLancamento tipo, String nomeCategoria, String nomePessoa, Long codigoPessoa,
			Long codigoCategoria) {
		this.codigo = codigo;
		this.descricao = descricao;
		this.dataVencimento = dataVencimento;
		this.dataPagamento = dataPagamento;
		this.valor = valor;
		this.tipo = tipo;
		this.nomeCategoria = nomeCategoria;
		this.nomePessoa = nomePessoa;
		this.codigoPessoa = codigoPessoa;
		this.codigoCategoria = codigoCategoria;
		this.pessoa = PessoaDTO.newInstance(codigoPessoa, nomePessoa);
		this.categoria = CategoriaDTO.newInstance(codigoCategoria, nomeCategoria);
		this.observacao = observacao;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public LocalDate getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(LocalDate dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public LocalDate getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(LocalDate dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public TipoLancamento getTipo() {
		return tipo;
	}

	public void setTipo(TipoLancamento tipo) {
		this.tipo = tipo;
	}

	public String getNomeCategoria() {
		return nomeCategoria;
	}

	public void setNomeCategoria(String nomeCategoria) {
		this.nomeCategoria = nomeCategoria;
	}

	public String getNomePessoa() {
		return nomePessoa;
	}

	public void setNomePessoa(String nomePessoa) {
		this.nomePessoa = nomePessoa;
	}

	public Long getCodigoCategoria() {
		return codigoCategoria;
	}

	public void setCodigoCategoria(Long codigoCategoria) {
		this.codigoCategoria = codigoCategoria;
	}

	public Long getCodigoPessoa() {
		return codigoPessoa;
	}

	public void setCodigoPessoa(Long codigoPessoa) {
		this.codigoPessoa = codigoPessoa;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public PessoaDTO getPessoa() {
		return pessoa;
	}

	public void setPessoa(PessoaDTO pessoa) {
		this.pessoa = pessoa;
	}

	public CategoriaDTO getCategoria() {
		return categoria;
	}

	public void setCategoria(CategoriaDTO categoria) {
		this.categoria = categoria;
	}

}
