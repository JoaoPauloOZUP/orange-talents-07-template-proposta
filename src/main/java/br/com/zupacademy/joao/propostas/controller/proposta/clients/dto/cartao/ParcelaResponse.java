package br.com.zupacademy.joao.propostas.controller.proposta.clients.dto.cartao;

public class ParcelaResponse {

    private String id;

    private Integer quantidade;

    private Double valor;

    public ParcelaResponse(String id, Integer quantidade, Double valor) {
        this.id = id;
        this.quantidade = quantidade;
        this.valor = valor;
    }
}
