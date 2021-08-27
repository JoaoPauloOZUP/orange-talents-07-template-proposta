package br.com.zupacademy.joao.propostas.controller.carteira;

import br.com.zupacademy.joao.propostas.controller.carteira.clients.CarteiraClient;
import br.com.zupacademy.joao.propostas.controller.carteira.dto.CarteiraPaypalRequest;
import br.com.zupacademy.joao.propostas.controller.carteira.dto.CarteiraSamsungPayRequest;
import br.com.zupacademy.joao.propostas.controller.carteira.dto.CarteirasDigitaisRequest;
import br.com.zupacademy.joao.propostas.controller.proposta.clients.dto.cartao.CarteiraResponse;
import br.com.zupacademy.joao.propostas.model.Cartao;
import br.com.zupacademy.joao.propostas.model.Carteira;
import br.com.zupacademy.joao.propostas.repository.CartaoRepository;
import br.com.zupacademy.joao.propostas.repository.CarteiraRepository;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.net.URI;
import java.util.Optional;

@RestController
public class CarteiraController {

    Logger logger = LoggerFactory.getLogger(CarteiraController.class);

    @Autowired
    private CartaoRepository cartaoRepository;

    @Autowired
    private CarteiraRepository carteiraRepository;

    @Autowired
    private CarteiraClient clienteCarteira;

    @Autowired
    private TransactionTemplate transaction;

    @PostMapping("/paypal/cartao/{id}")
    private ResponseEntity<?> associarCarteiraPayPal(@PathVariable("id") String numeroCartao,
                                                     @Valid @RequestBody CarteiraPaypalRequest request, UriComponentsBuilder builder) {

        return associarCarteira(numeroCartao, request, builder);
    }

    @PostMapping("/samsungpay/cartao/{id}")
    private ResponseEntity<?> associarCarteiraSamsungPay(@PathVariable("id") String numeroCartao,
                                            @Valid @RequestBody CarteiraSamsungPayRequest request, UriComponentsBuilder builder) {

        return associarCarteira(numeroCartao, request, builder);
    }

    private ResponseEntity<?> associarCarteira(@NotBlank String numeroCartao, @Valid CarteirasDigitaisRequest request, UriComponentsBuilder builder) {
        Optional<Cartao> possivelCartao = cartaoRepository.findByNumeroCartao(numeroCartao);

        if(possivelCartao.isPresent()) {
            Cartao cartao = possivelCartao.get();

            if(cartao.isAssociado()) {
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Cartão já está associado a uma carteira");
            }

            try {
                CarteiraResponse response = clienteCarteira.associar(numeroCartao, request);

                if(response.associacaoConcluida()){
                    Carteira carteira = request.toCarteira(cartao);
                    transaction.execute(status -> carteiraRepository.save(carteira));

                    URI uri = builder.path("carteiras/{id}").buildAndExpand(carteira.getId()).toUri();

                    return ResponseEntity.created(uri).build();
                }

                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Algo inesperado aconteceu");
            } catch (FeignException feignException) {
                logger.error("Erro na comunicação externa. ERRO={}", feignException.getMessage());
                return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Tente novamente mais tarde");
            }
        }

        return ResponseEntity.notFound().build();
    }
}
