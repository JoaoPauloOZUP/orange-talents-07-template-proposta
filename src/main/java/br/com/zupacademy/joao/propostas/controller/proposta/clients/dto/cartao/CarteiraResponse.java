package br.com.zupacademy.joao.propostas.controller.proposta.clients.dto.cartao;

import br.com.zupacademy.joao.propostas.controller.carteira.utils.EstadoSolicitacaoCarteira;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CarteiraResponse {

    @JsonProperty("resultado")
    private String resultado;

    @JsonProperty("id")
    private String id;

    public CarteiraResponse(String resultado, String id) {
        this.resultado = resultado;
        this.id = id;
    }

    public boolean associacaoConcluida() {
        return EstadoSolicitacaoCarteira.valueOf(resultado).resultado();
    }
}
