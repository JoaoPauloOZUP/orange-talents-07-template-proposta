package br.com.zupacademy.joao.propostas.controller.proposta.clients.dto.cartao;

import br.com.zupacademy.joao.propostas.controller.aviso_viagem.utils.EstadoAviso;
import br.com.zupacademy.joao.propostas.controller.aviso_viagem.utils.EstadoSolicitacaoAvisoViagem;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AvisoViagemResponse {

    @JsonProperty("resultado")
    private String resultado;

    public AvisoViagemResponse(@JsonProperty("resultado") String resultado) {
        this.resultado = resultado;
    }

    public EstadoAviso getResultado() {
        return EstadoSolicitacaoAvisoViagem.valueOf(resultado).resultado();
    }
}
