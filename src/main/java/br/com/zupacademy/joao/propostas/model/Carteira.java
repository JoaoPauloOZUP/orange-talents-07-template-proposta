package br.com.zupacademy.joao.propostas.model;

import br.com.zupacademy.joao.propostas.controller.carteira.utils.CarteirasDigitais;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class Carteira {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank @NotBlank
    private String email;

    @NotNull @Enumerated(EnumType.STRING)
    private CarteirasDigitais carteira;

    @OneToOne @NotNull
    private Cartao cartao;

    /**
     * @deprecated construtor padrão para o hibernate
     * */
    @Deprecated
    private Carteira() {
    }

    public Carteira(@NotBlank String email,
                    @NotBlank String carteira,
                    @NotNull Cartao cartao) {

        this.email = email;
        this.carteira = CarteirasDigitais.valueOf(carteira).obterCarteira();
        this.cartao = cartao;
    }

    public Long getId() {
        return id;
    }
}
