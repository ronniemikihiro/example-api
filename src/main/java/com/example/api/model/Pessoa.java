package com.example.api.model;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.example.api.repository.dto.PessoaDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Representa uma determinada pessoa.
 *
 */
@Entity
@Table(name = "pessoa")
public class Pessoa {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;

	@NotNull
	private String nome;

	@Embedded
	private Endereco endereco;

	@NotNull
	private Boolean ativo;
	
	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	/**
	 * Retorna uma nova instância de pessoa conforme o DTO correspondente informado.
	 * 
	 * @param pessoaDTO
	 * @return
	 */
	public static Pessoa newInstante(final PessoaDTO pessoaDTO) {
		Pessoa pessoa = null;

		if (pessoaDTO != null) {
			pessoa = new Pessoa();
			pessoa.setCodigo(pessoaDTO.getCodigo());
			pessoa.setNome(pessoaDTO.getNome());
			pessoa.setAtivo(pessoaDTO.getAtivo());
			pessoa.setEndereco(pessoaDTO.getEndereco());
		}

		return pessoa;
	}

	/**
	 * Verifica se o pessoa é inativa.
	 * 
	 * @return
	 */
	@JsonIgnore
	@Transient
	public boolean isInativo() {
		return getAtivo() == null || !getAtivo();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pessoa other = (Pessoa) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

}
