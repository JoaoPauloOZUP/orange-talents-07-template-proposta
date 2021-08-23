package br.com.zupacademy.joao.propostas.controller.proposta.clients.dto.cartao;

import br.com.zupacademy.joao.propostas.model.Proposta;

import javax.validation.constraints.NotNull;

public class CartaoRequest {

    private String documento;

    private String nome;

    private Long idProposta;

    public CartaoRequest() {
    }

    /**
     * @param proposta não deve ser nula para que avaliação seja sucedida
     * */
    public CartaoRequest(@NotNull Proposta proposta) {

        this.documento = proposta.getDocumento();
        this.nome = proposta.getNome();
        this.idProposta = proposta.getId();
    }

    public String getDocumento() {
        return documento;
    }

    public String getNome() {
        return nome;
    }

    public Long getIdProposta() {
        return idProposta;
    }
}
