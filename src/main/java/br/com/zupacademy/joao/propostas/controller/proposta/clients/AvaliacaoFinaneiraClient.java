package br.com.zupacademy.joao.propostas.controller.proposta.clients;

import br.com.zupacademy.joao.propostas.controller.proposta.clients.dto.AvaliacaoFinanceiraRequest;
import br.com.zupacademy.joao.propostas.controller.proposta.clients.dto.AvaliacaoFinanceiraResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "avaliacaoFinanceira", url = "http://localhost:9999/api/solicitacao")
public interface AvaliacaoFinaneiraClient {

    @PostMapping
    AvaliacaoFinanceiraResponse avalia(AvaliacaoFinanceiraRequest request);
}
