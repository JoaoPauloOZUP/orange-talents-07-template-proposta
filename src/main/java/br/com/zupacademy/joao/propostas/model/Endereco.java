package br.com.zupacademy.joao.propostas.model;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Embeddable
public class Endereco {

    @NotNull @NotBlank
    private String nomeRua;

    @NotNull @Positive
    private int numero;

    @NotNull @NotBlank
    private String nomeBairro;

    /**
     * @deprecated construtor padrão para o hibernate
     * */
    @Deprecated
    private Endereco() {}

    public Endereco(@NotNull @NotBlank String nomeRua,
                    @NotNull int numero,
                    @NotNull @NotBlank String nomeBairro) {

        this.nomeRua = nomeRua;
        this.numero = numero;
        this.nomeBairro = nomeBairro;
    }
}
