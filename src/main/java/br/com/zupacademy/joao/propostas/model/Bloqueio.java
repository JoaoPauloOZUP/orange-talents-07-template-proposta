package br.com.zupacademy.joao.propostas.model;

import br.com.zupacademy.joao.propostas.controller.bloqueio.clients.dto.BloqueioResponse;
import br.com.zupacademy.joao.propostas.controller.bloqueio.utils.EstadoSolicitacaoBloqueio;

import javax.persistence.*;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Entity
public class Bloqueio implements Comparable<Bloqueio> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne @NotNull
    private Cartao cartao;

    private Instant bloqueadoEm = Instant.now();

    private String ipClient;

    private String userAgent;

    @Enumerated(EnumType.STRING)
    private EstadoSolicitacaoBloqueio estadoBloqueio;

    /**
     * @deprecated construtor padrão para o hibernate
     * */
    public Bloqueio() {
    }

    public Bloqueio(Cartao cartao, HttpServletRequest servletRequest) {
        this.cartao = cartao;
        this.ipClient = servletRequest.getRemoteAddr();
        this.userAgent = servletRequest.getHeader("User-Agent");
    }

    public Long getId() {
        return id;
    }

    public EstadoSolicitacaoBloqueio getEstadoBloqueio() {
        return estadoBloqueio;
    }

    public void efetivar(BloqueioResponse response) {
        this.estadoBloqueio = response.getEstadoDoBloqueio();
    }

    public String numeroDoCartaoBloqueado() {
        return cartao.getNumeroCartao();
    }

    @Override
    public int compareTo(Bloqueio comparador) {
        if(this.id < comparador.getId()) {
            return -1;
        }

        if(this.id > comparador.getId()) {
            return 1;
        }

        return 0;
    }
}
