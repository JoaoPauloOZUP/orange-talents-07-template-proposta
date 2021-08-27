package br.com.zupacademy.joao.propostas.controller.carteira.dto;

import br.com.zupacademy.joao.propostas.model.Cartao;
import br.com.zupacademy.joao.propostas.model.Carteira;

public interface CarteirasDigitaisRequest {

    Carteira toCarteira(Cartao cartao);
}
