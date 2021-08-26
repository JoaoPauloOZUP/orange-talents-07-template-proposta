package br.com.zupacademy.joao.propostas.model;

import br.com.zupacademy.joao.propostas.controller.aviso_viagem.utils.EstadoAviso;

import javax.persistence.*;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class AvisoViagem {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank @NotNull
    private String destino;

    @NotNull
    @Future
    private LocalDate dataTermino;

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

    public AvisoViagem(String destino, LocalDate dataTermino, HttpServletRequest httpRequest, Cartao cartao) {
        this.destino = destino;
        this.dataTermino = dataTermino;
        this.ipClient = httpRequest.getRemoteAddr();
        this.userAgent = httpRequest.getHeader("User-Agent");
        this.cartao = cartao;
    }

    public Long getId() {
        return id;
    }
}
