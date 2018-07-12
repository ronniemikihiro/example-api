package com.example.api.exceptionhandler;

/**
 * Modelo representativo do Erro.
 */
public class Erro {

    private String propriedade;
    private String mensagem;

    /**
     * Construtor do modelo Erro.
     *
     * @param propriedade
     * @param mensagem
     */
    public Erro(final String propriedade, final String mensagem) {
        this.propriedade = propriedade;
        this.mensagem = mensagem;
    }

    public String getPropriedade() {
        return propriedade;
    }

    public String getMensagem() {
        return mensagem;
    }

}
