package br.com.zupacademy.joao.propostas.controller.proposta.clients.dto;

import br.com.zupacademy.joao.propostas.controller.proposta.utils.EstadoAvaliacao;
import br.com.zupacademy.joao.propostas.controller.proposta.utils.EstadoSolicitacao;

public class AvaliacaoFinanceiraResponse {
    private String documento;

    private String nome;

    private String resultadoSolicitacao;

    private Long idProposta;

    public AvaliacaoFinanceiraResponse() {
    }

    public AvaliacaoFinanceiraResponse(String documento, String nome, String resultadoSolicitacao, Long idProposta) {
        this.documento = documento;
        this.nome = nome;
        this.resultadoSolicitacao = resultadoSolicitacao;
        this.idProposta = idProposta;
    }

    public EstadoAvaliacao getResultadoSolicitacao() {
        return EstadoSolicitacao.valueOf(resultadoSolicitacao).resultado();
    }
}
