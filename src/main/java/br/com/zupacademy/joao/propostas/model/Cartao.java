package br.com.zupacademy.joao.propostas.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;

@Entity
public class Cartao {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull @NotBlank
    private String numeroCartao;

    @NotNull
    private LocalDateTime emitidoEm;

    @NotNull @NotBlank
    private String titular;

    @PositiveOrZero @NotNull
    private Integer limite;

    @OneToOne @NotNull
    private Proposta proposta;

    /**
     * @deprecated construtor padrão para o hibernate
     * */
    @Deprecated
    private Cartao() {
    }

    public Cartao(@NotBlank String numeroCartao,
                  @NotNull LocalDateTime emitidoEm,
                  @NotBlank String titular,
                  @NotNull Integer limite,
                  @NotNull Proposta proposta) {

        this.numeroCartao = numeroCartao;
        this.emitidoEm = emitidoEm;
        this.titular = titular;
        this.limite = limite;
        this.proposta = proposta;
    }
}
