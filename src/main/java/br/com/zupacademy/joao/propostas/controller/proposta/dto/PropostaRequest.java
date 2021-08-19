package br.com.zupacademy.joao.propostas.controller.proposta.dto;

import br.com.zupacademy.joao.propostas.clients.AvaliacaoFinaneiraClient;
import br.com.zupacademy.joao.propostas.clients.dto.AvaliacaoFinanceiraRequest;
import br.com.zupacademy.joao.propostas.clients.dto.AvaliacaoFinanceiraResponse;
import br.com.zupacademy.joao.propostas.controller.proposta.utils.EstadoAvaliacao;
import br.com.zupacademy.joao.propostas.model.Endereco;
import br.com.zupacademy.joao.propostas.model.Proposta;
import br.com.zupacademy.joao.propostas.validator.documento.CPFOuCNPJ;

import javax.validation.constraints.*;

public class PropostaRequest {

    @NotNull @CPFOuCNPJ
    private String documento;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String nome;

    @NotBlank
    private String rua;

    private int numero;

    @NotBlank
    private String bairro;

    @PositiveOrZero @NotNull
    private Double salario;

    private PropostaRequest(String documento, String email, String nome, String rua, int numero, String bairro, Double salario) {
        this.documento = documento.replaceAll("[^0-9]", "");
        this.email = email;
        this.nome = nome;
        this.rua = rua;
        this.numero = numero;
        this.bairro = bairro;
        this.salario = salario;
    }

    public Proposta toProposta() {
        return new Proposta(documento, email, nome, new Endereco(rua, numero,  bairro), salario);
    }

    public String getDocumento() {
        return documento;
    }
}
