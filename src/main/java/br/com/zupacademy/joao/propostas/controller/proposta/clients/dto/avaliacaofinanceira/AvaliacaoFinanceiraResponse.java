package br.com.zupacademy.joao.propostas.controller.proposta.clients.dto.avaliacaofinanceira;

import br.com.zupacademy.joao.propostas.controller.proposta.utils.EstadoAvaliacaoDaProposta;
import br.com.zupacademy.joao.propostas.controller.proposta.utils.EstadoSolicitacaoDaProposta;

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

    public EstadoAvaliacaoDaProposta getResultadoSolicitacao() {
        return EstadoSolicitacaoDaProposta.valueOf(resultadoSolicitacao).resultado();
    }
}
