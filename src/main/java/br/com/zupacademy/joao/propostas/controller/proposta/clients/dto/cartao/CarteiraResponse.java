package br.com.zupacademy.joao.propostas.controller.proposta.clients.dto.cartao;

public class CarteiraResponse {

    private String id;

    private String email;

    private String associadoEm;

    private String emissor;

    public CarteiraResponse(String id, String email, String associadoEm, String emissor) {
        this.id = id;
        this.email = email;
        this.associadoEm = associadoEm;
        this.emissor = emissor;
    }
}
