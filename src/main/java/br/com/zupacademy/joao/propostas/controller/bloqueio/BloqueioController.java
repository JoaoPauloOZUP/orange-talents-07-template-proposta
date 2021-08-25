package br.com.zupacademy.joao.propostas.controller.bloqueio;

import br.com.zupacademy.joao.propostas.config.exception.ApiErroException;
import br.com.zupacademy.joao.propostas.controller.bloqueio.clients.BloqueioClient;
import br.com.zupacademy.joao.propostas.controller.bloqueio.clients.dto.BloqueioResponse;
import br.com.zupacademy.joao.propostas.controller.bloqueio.clients.dto.SistemaResponsavelRequest;
import br.com.zupacademy.joao.propostas.controller.proposta.PropostaController;
import br.com.zupacademy.joao.propostas.model.Bloqueio;
import br.com.zupacademy.joao.propostas.model.Cartao;
import br.com.zupacademy.joao.propostas.repository.BloqueioRepository;
import br.com.zupacademy.joao.propostas.repository.CartaoRepository;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
public class BloqueioController {

    private final Logger logger = LoggerFactory.getLogger(PropostaController.class);

    @Autowired
    private CartaoRepository cartaoRepository;

    @Autowired
    private TransactionTemplate transaction;

    @Autowired
    private BloqueioRepository bloqueioRepository;

    @Autowired
    private BloqueioClient clientBloqueio;

    @PutMapping("/bloqueio/cartao/{id}")
    private ResponseEntity<?> bloquearCartao(@PathVariable("id") String numeroCartao, HttpServletRequest servletRequest) {
        Optional<Bloqueio> possivelBloqueio = bloqueioRepository.findByCartaoNumeroCartao(numeroCartao);

        if(possivelBloqueio.isPresent()) {
            logger.error("Cartão já bloqueado. BLOQUEIO={}", possivelBloqueio.get().getId());
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Este cartão já está bloqueado");
        }

        Optional<Cartao> possivelCartao = cartaoRepository.findByNumeroCartao(numeroCartao);

        if(possivelCartao.isPresent()) {
            Cartao cartao = possivelCartao.get();
            Bloqueio bloqueio = new Bloqueio(cartao, servletRequest);
            try{
                BloqueioResponse response = clientBloqueio.bloquear(numeroCartao, new SistemaResponsavelRequest("propostas"));
                bloqueio.efetivar(response);
                logger.info("Bloqueio realizado. CARTAO={}, ESTADO={}", cartao.getId(), bloqueio.getEstadoBloqueio());
            } catch (FeignException.FeignClientException exception) {
                // Trato a exceção caso haja algum erro e digo que o bloqueio em um estado de espera!
                bloqueio.emEspera();
                logger.error("Erro na comunicação com serviço externo. ERROR={}, CAUSA={}", exception.getMessage(), exception.getCause().getMessage());
                throw new ApiErroException(HttpStatus.SERVICE_UNAVAILABLE, "Aconteceu algo de errado. Seu cartão será bloqueado periódicamente");
            } finally {
                // Independente do que houver, sempre salvarei o cartão e o estado do bloqueio, seja em FALHA ou EM_ESPERA!
                cartao.bloquear(bloqueio);
                transaction.execute(status -> cartaoRepository.save(cartao));
                logger.info("Cartão salvo. CARTAO={}, ESTADO={}", cartao.getId(), bloqueio.getEstadoBloqueio());
            }

            // Só chegará a este ponto caso tudo ocorra certo!
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }
}
