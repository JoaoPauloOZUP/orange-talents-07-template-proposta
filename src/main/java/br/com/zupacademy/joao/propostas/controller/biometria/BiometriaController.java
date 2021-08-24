package br.com.zupacademy.joao.propostas.controller.biometria;

import br.com.zupacademy.joao.propostas.config.exception.ApiErroException;
import br.com.zupacademy.joao.propostas.controller.biometria.dto.BiometiaRequest;
import br.com.zupacademy.joao.propostas.model.Biometria;
import br.com.zupacademy.joao.propostas.model.Cartao;
import br.com.zupacademy.joao.propostas.repository.BiometriaRepository;
import br.com.zupacademy.joao.propostas.repository.CartaoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class BiometriaController {

    private final Logger logger = LoggerFactory.getLogger(BiometriaController.class);

    @Autowired
    private CartaoRepository cataoRepository;

    @Autowired
    private BiometriaRepository biometriaRepository;

    @Autowired
    private TransactionTemplate transaction;

    @PostMapping("/biometria/cartao/{id}")
    private ResponseEntity<?> cadastrarBiometria(@PathVariable("id") String numeroCartao, @Valid BiometiaRequest request, UriComponentsBuilder builder) {
        Optional<Cartao> possivelCartao = cataoRepository.findByNumeroCartao(numeroCartao);

        if(possivelCartao.isPresent()) {
            try {
                Biometria biometria = request.toBiometria(possivelCartao.get());
                transaction.execute(status -> biometriaRepository.save(biometria));
                logger.info("Biometria salva. Biometria={}", biometria.getId());

                URI uri = builder.path("/biometria/{id}").buildAndExpand(biometria.getId()).toUri();

                return ResponseEntity.created(uri).build();
            } catch (Exception exception) {
                logger.error("Erro. ERRO={}, CAUSA={}", exception.getMessage(), exception.getCause().getMessage());
                throw new ApiErroException(HttpStatus.UNPROCESSABLE_ENTITY, "Algo deu errado, tente novamente mais tarde");
            }
        }

        return ResponseEntity.notFound().build();
    }
}
