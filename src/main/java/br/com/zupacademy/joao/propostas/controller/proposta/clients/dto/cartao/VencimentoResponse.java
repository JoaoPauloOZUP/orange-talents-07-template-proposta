package br.com.zupacademy.joao.propostas.controller.proposta.clients.dto.cartao;

public class VencimentoResponse {

    private String id;

    private Integer dia;

    private String dataCriacao;

    public VencimentoResponse(String id, Integer dia, String dataCriacao) {
        this.id = id;
        this.dia = dia;
        this.dataCriacao = dataCriacao;
    }
}
