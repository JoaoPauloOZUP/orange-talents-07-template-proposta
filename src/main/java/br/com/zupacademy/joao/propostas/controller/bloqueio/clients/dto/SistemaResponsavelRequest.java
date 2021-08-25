package br.com.zupacademy.joao.propostas.controller.bloqueio.clients.dto;

public class SistemaResponsavelRequest {

    private String sistemaResponsavel;

    public SistemaResponsavelRequest(String localname) {
        this.sistemaResponsavel = localname;
    }

    public String getSistemaResponsavel() {
        return sistemaResponsavel;
    }
}
