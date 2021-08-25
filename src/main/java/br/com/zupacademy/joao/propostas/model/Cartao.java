package br.com.zupacademy.joao.propostas.model;

import br.com.zupacademy.joao.propostas.controller.bloqueio.utils.EstadoBloqueio;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import static br.com.zupacademy.joao.propostas.controller.bloqueio.utils.EstadoBloqueio.EFETIVADO;

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

    @OneToOne(cascade = CascadeType.MERGE, mappedBy = "cartao")
    private Bloqueio bloqueio;

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

    public void bloquear(Bloqueio bloqueio) {
        this.bloqueio = bloqueio;
    }

    public Long getId() {
        return id;
    }

    public String getNumeroCartao() {
        return numeroCartao;
    }

    public boolean isBloqueado() {
        return bloqueio != null || bloqueio.getEstadoBloqueio().equals(EFETIVADO);
    }
}
