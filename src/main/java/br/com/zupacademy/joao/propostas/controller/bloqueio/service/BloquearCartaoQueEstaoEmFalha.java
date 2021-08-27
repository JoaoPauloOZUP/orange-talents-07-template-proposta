package br.com.zupacademy.joao.propostas.controller.bloqueio.service;

import br.com.zupacademy.joao.propostas.controller.bloqueio.clients.BloqueioClient;
import br.com.zupacademy.joao.propostas.controller.bloqueio.clients.dto.BloqueioResponse;
import br.com.zupacademy.joao.propostas.controller.bloqueio.clients.dto.SistemaResponsavelRequest;
import br.com.zupacademy.joao.propostas.model.Bloqueio;
import br.com.zupacademy.joao.propostas.repository.BloqueioRepository;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.support.TransactionTemplate;

import static br.com.zupacademy.joao.propostas.controller.bloqueio.utils.EstadoBloqueio.FALHA;

import java.util.List;

@Service
public class BloquearCartaoQueEstaoEmFalha {

    private final Logger logger = LoggerFactory.getLogger(BloquearCartaoQueEstaoEmFalha.class);

    @Autowired
    private BloqueioClient bloqueioClient;

    @Autowired
    private BloqueioRepository bloqueioRepository;

    @Autowired
    private TransactionTemplate transaction;

    @Scheduled(fixedDelay = 60000)
    protected void bloquearCartoesEmEspera() {
        // Tudo bem, tratei os erros e aqui sempre busco um bloqueio não efetivado com estado de FALHA ou EM_ESPERA
        List<Bloqueio> possiveisBloqueiosEmEspera = bloqueioRepository.findByEstadoBloqueio(FALHA.name());

        if(possiveisBloqueiosEmEspera.size() != 0) {
            possiveisBloqueiosEmEspera.forEach(bloqueio -> {
                try {
                    BloqueioResponse response = bloqueioClient.bloquear(bloqueio.numeroDoCartaoBloqueado(), new SistemaResponsavelRequest("propostas"));
                    bloqueio.efetivar(response);
                } catch(FeignException feignException) {
                    logger.error("Erro na comunicação. ERRO={}", feignException.getMessage());
                }

                try {
                    transaction.execute(status -> bloqueioRepository.save(bloqueio));
                    logger.info("Bloqueio efetivado BLOQUEIO={}, ESTADO={}", bloqueio.getId(), bloqueio.getEstadoBloqueio());
                } catch (TransactionException transactionException) {
                    logger.error("Erro ao persistir o dado. ERRO={}, CAUSA={}", transactionException.getMessage(), transactionException.getCause().getMessage());
                }
            });
        }
    }
}
