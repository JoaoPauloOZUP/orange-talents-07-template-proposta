package br.com.zupacademy.joao.propostas.model;

import br.com.zupacademy.joao.propostas.controller.bloqueio.utils.EstadoSolicitacaoBloqueio;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static br.com.zupacademy.joao.propostas.controller.bloqueio.utils.EstadoSolicitacaoBloqueio.BLOQUEADO;

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

    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "cartao")
    private List<Bloqueio> bloqueios = new ArrayList<>();

    @OneToMany(mappedBy = "cartao")
    private List<AvisoViagem> avisos = new ArrayList<>();

    @OneToOne(mappedBy = "cartao")
    private Carteira carteira;

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

    public Long getId() {
        return id;
    }

    public String getNumeroCartao() {
        return numeroCartao;
    }

    public void bloquear(Bloqueio bloqueio) {
        this.bloqueios.add(bloqueio);
    }

    public void incluirAvisoDeViagem(AvisoViagem aviso) {
        this.avisos.add(aviso);
    }

    public boolean isBloqueado() {
        // Considero as solicitações. Caso não tenha nenhuma, então a lista estará vazia
        if(bloqueios.isEmpty()) {
            return false;
        } else {
            // Em uma lista de bloqueios, considero que a ultima solicitação é que vale.
            // Para garantir isso implementei o comparTo pelo o identificador.
            bloqueios.sort(Bloqueio::compareTo);
            int ultimo = bloqueios.size()-1;
            return bloqueios.get(ultimo).getEstadoBloqueio().equals(BLOQUEADO);
        }
    }

    public boolean isAssociado() {
        return carteira != null;
    }
}

