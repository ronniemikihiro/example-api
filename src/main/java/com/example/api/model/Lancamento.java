package com.example.api.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.example.api.repository.dto.LancamentoDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Entidade Lancamento.
 */
@Entity
@Table(name = "lancamento")
public class Lancamento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;

	@NotNull
	private String descricao;

	@NotNull
	@Column(name = "data_vencimento")
	private LocalDate dataVencimento;

	@Column(name = "data_pagamento")
	private LocalDate dataPagamento;

	@NotNull
	private BigDecimal valor;

	private String observacao;

	@NotNull
	@Enumerated(EnumType.STRING)
	private TipoLancamento tipo;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "codigo_categoria")
	private Categoria categoria;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "codigo_pessoa")
	private Pessoa pessoa;

	/**
	 * Retorna uma nova instancia de Lancamento conforme o DTO informado
	 * 
	 * @param lancamentoDTO
	 * @return
	 */
	public static Lancamento newInstance(final LancamentoDTO lancamentoDTO) {
		Lancamento lancamento = null;

		if (lancamentoDTO != null) {
			lancamento = new Lancamento();
			Categoria categoria = Categoria.newInstante(lancamentoDTO.getCategoria());
			lancamento.setCategoria(categoria);
			Pessoa pessoa = Pessoa.newInstante(lancamentoDTO.getPessoa());
			lancamento.setPessoa(pessoa);
			lancamento.setDataPagamento(lancamentoDTO.getDataPagamento());
			lancamento.setDataVencimento(lancamentoDTO.getDataVencimento());
			lancamento.setObservacao(lancamentoDTO.getObservacao());
			lancamento.setTipo(lancamentoDTO.getTipo());
			lancamento.setValor(lancamentoDTO.getValor());
			lancamento.setDescricao(lancamentoDTO.getDescricao());
		}

		return lancamento;
	}

	/**
	 * Verifica se o lançamento tem alguma pessoa vinculada.
	 * 
	 * @return
	 */
	@JsonIgnore
	@Transient
	public boolean temPessoaVinculada() {
		return getPessoa() != null;
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

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public TipoLancamento getTipo() {
		return tipo;
	}

	public void setTipo(TipoLancamento tipo) {
		this.tipo = tipo;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	/**
	 * HashCode sobrescrito.
	 *
	 * @return int
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		return result;
	}

	/**
	 * Equals sobrescrito.
	 *
	 * @param obj
	 * @return boolean
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Lancamento other = (Lancamento) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

}
