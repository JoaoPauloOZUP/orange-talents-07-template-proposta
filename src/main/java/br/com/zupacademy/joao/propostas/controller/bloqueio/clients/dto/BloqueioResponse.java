package br.com.zupacademy.joao.propostas.controller.bloqueio.clients.dto;

import br.com.zupacademy.joao.propostas.controller.bloqueio.utils.EstadoBloqueio;
import br.com.zupacademy.joao.propostas.controller.bloqueio.utils.EstadoSolicitacaoBloqueio;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BloqueioResponse {

    @JsonProperty("resultado")
    private String resultado;

    public BloqueioResponse(@JsonProperty("resultado") String resultado) {
        this.resultado = resultado;
    }

    public EstadoSolicitacaoBloqueio getEstadoDoBloqueio() {
        return EstadoSolicitacaoBloqueio.valueOf(resultado).resultado();
    }
}
