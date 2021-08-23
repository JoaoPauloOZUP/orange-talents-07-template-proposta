package br.com.zupacademy.joao.propostas.controller.proposta.clients;

import br.com.zupacademy.joao.propostas.controller.proposta.clients.dto.cartao.CartaoRequest;
import br.com.zupacademy.joao.propostas.controller.proposta.clients.dto.cartao.CartaoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "cartao", url = "${cartoes.host}")
public interface CartaoClient {

    @PostMapping
    CartaoResponse obterCartao(CartaoRequest request);
}
