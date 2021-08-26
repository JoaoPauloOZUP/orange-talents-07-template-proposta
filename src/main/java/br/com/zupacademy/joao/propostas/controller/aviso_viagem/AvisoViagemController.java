package br.com.zupacademy.joao.propostas.controller.aviso_viagem;

import br.com.zupacademy.joao.propostas.controller.aviso_viagem.clients.AvisoClient;
import br.com.zupacademy.joao.propostas.controller.aviso_viagem.dto.AvisoViagemRequest;
import br.com.zupacademy.joao.propostas.controller.proposta.clients.dto.cartao.AvisoViagemResponse;
import br.com.zupacademy.joao.propostas.model.AvisoViagem;
import br.com.zupacademy.joao.propostas.model.Cartao;
import br.com.zupacademy.joao.propostas.repository.CartaoRepository;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;

@RestController
public class AvisoViagemController {

    private final Logger logger = LoggerFactory.getLogger(AvisoViagemController.class);

    @Autowired
    private CartaoRepository cartaoRepository;

    @Autowired
    private TransactionTemplate transaction;

    @Autowired
    private AvisoClient clientAviso;

    @PutMapping("avisoviagem/cartao/{id}")
    private ResponseEntity<?> avisarViagem(@PathVariable("id") String numeroCartao, @RequestBody @Valid AvisoViagemRequest request, HttpServletRequest httpRequest) {
        Optional<Cartao> possivelCartao = cartaoRepository.findByNumeroCartao(numeroCartao);

        if(possivelCartao.isPresent()) {
            Cartao cartao = possivelCartao.get();
            AvisoViagem aviso = request.toAvisoViagem(httpRequest, cartao);

            try {
                AvisoViagemResponse response = clientAviso.avisar(aviso.getNumeroCartao(), new AvisoViagemRequest(aviso.getDestino(), aviso.getValidoAte()));
                aviso.atualizarAviso(response);
            } catch (FeignException feignException) {
                logger.error("Erro na comunicação externa. ERRO={}", feignException.getMessage());
                return ResponseEntity.status(feignException.status()).build();
            }

            try {
                cartao.incluirAvisoDeViagem(aviso);
                transaction.execute(status -> cartaoRepository.save(cartao));
                logger.info("Aviso atualizado. ESTADO={}", aviso.getEstadoAviso());
            } catch (TransactionException transactionException) {
                logger.error("Erro ao persistir. AVISO={}, ESTADO={}", aviso.getId(), aviso.getEstadoAviso());
                logger.error("Erro ao persistir. ERRO={}, CAUSA={}", transactionException.getMessage(), transactionException.getCause().getMessage());
                return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Houve um erro. Tente mais tarde");
            }

            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }
}
