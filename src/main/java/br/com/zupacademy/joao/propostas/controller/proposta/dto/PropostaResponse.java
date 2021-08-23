package br.com.zupacademy.joao.propostas.controller.proposta.dto;

import br.com.zupacademy.joao.propostas.model.Proposta;

public class PropostaResponse {

    private String estadoAvaliacao;

    public PropostaResponse(Proposta proposta) {
        this.estadoAvaliacao = proposta.getEstadoAvaliacao().name();
    }

    public String getEstadoAvaliacao() {
        return estadoAvaliacao;
    }
}
