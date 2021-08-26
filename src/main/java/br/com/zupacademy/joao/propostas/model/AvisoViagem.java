package br.com.zupacademy.joao.propostas.model;

import br.com.zupacademy.joao.propostas.controller.aviso_viagem.utils.EstadoAviso;
import br.com.zupacademy.joao.propostas.controller.proposta.clients.dto.cartao.AvisoViagemResponse;

import javax.persistence.*;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class AvisoViagem {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank @NotNull
    private String destino;

    @NotNull
    @Future
    private LocalDate validoAte;

    @NotNull
    private LocalDateTime dataDoAviso = LocalDateTime.now();

    @NotBlank @NotNull
    private String ipClient;

    @NotBlank @NotNull
    private String userAgent;

    @NotNull
    @ManyToOne
    private Cartao cartao;

    @Enumerated(EnumType.STRING)
    private EstadoAviso estadoAviso = EstadoAviso.EM_EMPERA;

    /**
     * @deprecated construtor padrão para o hibernate
     * */
    @Deprecated
    private AvisoViagem() {

    }

    public AvisoViagem(String destino, LocalDate validoAte, HttpServletRequest httpRequest, Cartao cartao) {
        this.destino = destino;
        this.validoAte = validoAte;
        this.ipClient = httpRequest.getRemoteAddr();
        this.userAgent = httpRequest.getHeader("User-Agent");
        this.cartao = cartao;
    }

    public Long getId() {
        return id;
    }

    public String getDestino() {
        return destino;
    }

    public LocalDate getValidoAte() {
        return validoAte;
    }

    public String getNumeroCartao() {
        return cartao.getNumeroCartao();
    }

    public EstadoAviso getEstadoAviso() {
        return estadoAviso;
    }

    public void atualizarAviso(AvisoViagemResponse response) {
        this.estadoAviso = response.getResultado();
    }
}
