package br.com.zupacademy.joao.propostas.controller.aviso_viagem;

import br.com.zupacademy.joao.propostas.controller.aviso_viagem.dto.AvisoViagemRequest;
import br.com.zupacademy.joao.propostas.model.AvisoViagem;
import br.com.zupacademy.joao.propostas.model.Cartao;
import br.com.zupacademy.joao.propostas.repository.CartaoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PutMapping("avisoviagem/cartao/{id}")
    private ResponseEntity<?> avisarViagem(@PathVariable("id") String numeroCartao, @RequestBody @Valid AvisoViagemRequest request, HttpServletRequest httpRequest) {
        Optional<Cartao> possivelCartao = cartaoRepository.findByNumeroCartao(numeroCartao);

        if(possivelCartao.isPresent()) {
            Cartao cartao = possivelCartao.get();
            AvisoViagem aviso = request.toAvisoViagem(httpRequest, cartao);
            cartao.incluirAvisoDeViagem(aviso);
            try {
                transaction.execute(status ->  cartaoRepository.save(cartao));
                logger.info("Aviso incluido com sucesso. AVISO={}", aviso.getId());
            } catch (TransactionException transactionException) {
                logger.error("Erro ao persistir o dado. ERRO={}, CAUSA={}", transactionException.getMessage(), transactionException.getCause().getMessage());
            }

            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }
}
