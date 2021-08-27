package br.com.zupacademy.joao.propostas.controller.carteira.dto;

import br.com.zupacademy.joao.propostas.model.Cartao;
import br.com.zupacademy.joao.propostas.model.Carteira;
import br.com.zupacademy.joao.propostas.model.Proposta;
import br.com.zupacademy.joao.propostas.validator.carteira.CarteiraValidator;
import br.com.zupacademy.joao.propostas.validator.email.ExistEmail;
import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class CarteiraSamsungPayRequest implements CarteirasDigitaisRequest {

    @ExistEmail(domainClass = Proposta.class, fieldName = "email")
    @Email
    @NotBlank
    private String email;

    @CarteiraValidator(carteiraEsperada = "SAMSUNGPAY")
    @NotBlank
    private String carteira;

    @Deprecated
    private CarteiraSamsungPayRequest() {

    }

    @JsonCreator
    public CarteiraSamsungPayRequest(String email, String carteira) {
        this.email = email;
        this.carteira = carteira.toUpperCase();
    }

    public Carteira toCarteira(Cartao cartao) {
        return new Carteira(email, carteira, cartao);

    }

    public String getEmail() {
        return email;
    }

    public String getCarteira() {
        return carteira;
    }
}
