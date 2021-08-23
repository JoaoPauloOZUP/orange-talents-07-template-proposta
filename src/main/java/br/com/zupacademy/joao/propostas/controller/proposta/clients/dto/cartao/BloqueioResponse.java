package br.com.zupacademy.joao.propostas.controller.proposta.clients.dto.cartao;

public class BloqueioResponse {

    private String id;

    private String bloqueadoEm;

    private String sistemaResponsavel;

    private boolean ativo;

    public BloqueioResponse(String id, String bloqueadoEm, String sistemaResponsavel, boolean ativo) {
        this.id = id;
        this.bloqueadoEm = bloqueadoEm;
        this.sistemaResponsavel = sistemaResponsavel;
        this.ativo = ativo;
    }
}
