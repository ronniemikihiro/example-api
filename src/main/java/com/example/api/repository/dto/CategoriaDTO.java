package com.example.api.repository.dto;

/**
 * Classe de transfêrencia com as informações básicas de categoria.
 *
 */
public class CategoriaDTO {

	private Long codigo;
	private String nome;

	/**
	 * Retorna uma nova instancia de {@link CategoriaDTO} conforme os valore
	 * informados.
	 * 
	 * @param codigoCategoria
	 * @param nomeCategoria
	 * @return
	 */
	public static CategoriaDTO newInstance(final Long codigoCategoria, final String nomeCategoria) {
		CategoriaDTO categoria = null;

		if (codigoCategoria != null) {
			categoria = new CategoriaDTO();
			categoria.setCodigo(codigoCategoria);
			categoria.setNome(nomeCategoria);
		}

		return categoria;
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

}
