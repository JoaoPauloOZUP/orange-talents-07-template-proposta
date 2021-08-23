package br.com.zupacademy.joao.propostas.controller.proposta.clients.dto.cartao;

public class AvisoViagemResponse {

    private String validoAte;

    private String destino;

    public AvisoViagemResponse(String validoAte, String destino) {
        this.validoAte = validoAte;
        this.destino = destino;
    }
}
