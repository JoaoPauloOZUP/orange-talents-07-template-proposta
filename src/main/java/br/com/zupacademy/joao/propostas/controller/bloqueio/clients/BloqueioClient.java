package br.com.zupacademy.joao.propostas.controller.bloqueio.clients;

import br.com.zupacademy.joao.propostas.controller.bloqueio.clients.dto.BloqueioResponse;
import br.com.zupacademy.joao.propostas.controller.bloqueio.clients.dto.SistemaResponsavelRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "bloqueio", url = "${cartoes.host}")
public interface BloqueioClient {

    @PostMapping("/{id}/bloqueios")
    BloqueioResponse bloquear(@RequestParam("id") String numeroCartao, @RequestBody SistemaResponsavelRequest request);
}
