package br.com.zupacademy.joao.propostas.controller.proposta.dto;

import br.com.zupacademy.joao.propostas.controller.proposta.utils.DocumentoEncode;
import br.com.zupacademy.joao.propostas.model.Endereco;
import br.com.zupacademy.joao.propostas.model.Proposta;
import br.com.zupacademy.joao.propostas.validator.documento.CPFOuCNPJ;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.validation.constraints.*;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

public class PropostaRequest {

    @NotNull @CPFOuCNPJ @NotBlank
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

    public Proposta toProposta() throws NoSuchPaddingException, NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        DocumentoEncode documentoEncode = new DocumentoEncode();
        String documentoEncript = documentoEncode.encodar(documento);
        return new Proposta(documentoEncript, email, nome, new Endereco(rua, numero,  bairro), salario);
    }

    public String getDocumento() {
        return documento;
    }
}
