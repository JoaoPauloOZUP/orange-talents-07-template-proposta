package br.com.zupacademy.joao.propostas.controller.aviso_viagem.clients;

import br.com.zupacademy.joao.propostas.controller.aviso_viagem.dto.AvisoViagemRequest;
import br.com.zupacademy.joao.propostas.controller.proposta.clients.dto.cartao.AvisoViagemResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "avisoViagem", url = "${cartoes.host}")
public interface AvisoClient {

    @PostMapping("/{id}/avisos")
    AvisoViagemResponse avisar(@RequestParam("id") String numeroCartao, @RequestBody AvisoViagemRequest request);
}
