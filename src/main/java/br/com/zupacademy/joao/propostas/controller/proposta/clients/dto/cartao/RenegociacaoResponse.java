package br.com.zupacademy.joao.propostas.controller.proposta.clients.dto.cartao;

public class RenegociacaoResponse {

    private String id;

    private Integer quantidade;

    private Double valor;

    private String dataCriacao;

    public RenegociacaoResponse(String id, Integer quantidade, Double valor, String dataCriacao) {
        this.id = id;
        this.quantidade = quantidade;
        this.valor = valor;
        this.dataCriacao = dataCriacao;
    }
}
