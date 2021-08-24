package br.com.zupacademy.joao.propostas.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Entity
public class Biometria {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull @NotBlank
    private String biometria;

    @NotNull
    @ManyToOne
    private Cartao cartao;

    @NotNull
    private Instant instant = Instant.now();

    public Biometria(@NotBlank String biometria,
                     @NotNull Cartao cartao) {

        this.biometria = biometria;
        this.cartao = cartao;
    }

    public Long getId() {
        return id;
    }
}
