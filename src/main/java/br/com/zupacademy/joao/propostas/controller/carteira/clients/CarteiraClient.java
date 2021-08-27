package br.com.zupacademy.joao.propostas.controller.carteira.clients;

import br.com.zupacademy.joao.propostas.controller.carteira.dto.CarteiraRequest;
import br.com.zupacademy.joao.propostas.controller.proposta.clients.dto.cartao.CarteiraResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "carteira", url = "${cartoes.host}")
public interface CarteiraClient {

    @PostMapping("/{id}/carteiras")
    CarteiraResponse associar(@RequestParam("id") String numeroCartao, @RequestBody CarteiraRequest request);
}
