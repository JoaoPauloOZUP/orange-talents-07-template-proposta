package br.com.zupacademy.joao.propostas.controller.proposta.clients;

import br.com.zupacademy.joao.propostas.controller.proposta.clients.dto.avaliacaofinanceira.AvaliacaoFinanceiraRequest;
import br.com.zupacademy.joao.propostas.controller.proposta.clients.dto.avaliacaofinanceira.AvaliacaoFinanceiraResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "avaliacaoFinanceira", url = "${avaliacoes.host}")
public interface AvaliacaoFinaneiraClient {

    @PostMapping
    AvaliacaoFinanceiraResponse avalia(AvaliacaoFinanceiraRequest request);
}
