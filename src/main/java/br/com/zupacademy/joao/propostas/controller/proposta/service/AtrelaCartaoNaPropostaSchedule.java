package br.com.zupacademy.joao.propostas.controller.proposta.service;

import br.com.zupacademy.joao.propostas.controller.proposta.clients.CartaoClient;
import br.com.zupacademy.joao.propostas.controller.proposta.clients.dto.cartao.CartaoRequest;
import br.com.zupacademy.joao.propostas.controller.proposta.clients.dto.cartao.CartaoResponse;
import br.com.zupacademy.joao.propostas.model.Cartao;
import br.com.zupacademy.joao.propostas.model.Proposta;
import br.com.zupacademy.joao.propostas.repository.PropostaRepository;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.support.TransactionTemplate;

import static br.com.zupacademy.joao.propostas.controller.proposta.utils.EstadoCartao.AGUARDANDO_CARTAO;

import java.util.List;

@Service
public class AtrelaCartaoNaPropostaSchedule {

    private final Logger logger = LoggerFactory.getLogger(AtrelaCartaoNaPropostaSchedule.class);

    @Autowired
    private PropostaRepository repository;

    @Autowired
    private CartaoClient clientCartao;

    @Autowired
    private TransactionTemplate transaction;

    @Scheduled(fixedDelay = 5000)
    private void atrelarCartaoComProposta() {
        List<Proposta> possiveisPropostas = repository.findByEstadoCartao(AGUARDANDO_CARTAO.name());

        if(possiveisPropostas.size() != 0) {
            possiveisPropostas.forEach(proposta -> {
                try {
                    CartaoResponse response = clientCartao.obterCartao(new CartaoRequest(proposta));
                    Cartao cartao = response.toCartao(repository);
                    proposta.incluirCartao(cartao);
                } catch (FeignException.FeignClientException exception) {
                    logger.error("Erro na comunicação com API externa. PROPOSTA={} STATUS={}, ERRO={}", proposta.getId(), exception.status(), exception.getMessage());
                }

                try {
                    transaction.execute(status -> repository.save(proposta));
                    logger.info("Proposta com cartão atrelado PROPOSTA={}", proposta.getId());
                } catch (TransactionException exception) {
                    logger.error("Erro ao persistir. ERRO={}, CAUSA={}", exception.getMessage(), exception.getCause().getMessage());
                }
            });
        }
    }
}
