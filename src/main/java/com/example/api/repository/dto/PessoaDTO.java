package com.example.api.repository.dto;

import com.example.api.model.Endereco;

/**
 * Classe de transfêrencia com as informações básicas de lançamento.
 *
 */
public class PessoaDTO {

	private Long codigo;
	private String nome;
	private Endereco endereco;
	private Boolean ativo;
	private String cpf;

	/**
	 * Construtor padrão
	 */
	public PessoaDTO() {

	}

	/**
	 * 
	 */
	public PessoaDTO(Long codigo, String nome, Boolean ativo, Endereco endereco) {
		this.codigo = codigo;
		this.nome = nome;
		this.ativo = ativo;
		this.endereco = endereco;
	}

	/**
	 * Retorna uma nova instancia de {@link PessoaDTO} conforme os valore
	 * informados.
	 * 
	 * @param codigoPessoa
	 * @param nomePessoa
	 * @return
	 */
	public static PessoaDTO newInstance(final Long codigoPessoa, final String nomePessoa) {
		PessoaDTO pessoa = null;

		if (codigoPessoa != null) {
			pessoa = new PessoaDTO();
			pessoa.setCodigo(codigoPessoa);
			pessoa.setNome(nomePessoa);
		}

		return pessoa;
	}

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

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

}
