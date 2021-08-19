package br.com.zupacademy.joao.propostas.controller.proposta;

import br.com.zupacademy.joao.propostas.controller.proposta.clients.AvaliacaoFinaneiraClient;
import br.com.zupacademy.joao.propostas.config.exception.ApiErroException;
import br.com.zupacademy.joao.propostas.controller.proposta.clients.dto.AvaliacaoFinanceiraRequest;
import br.com.zupacademy.joao.propostas.controller.proposta.clients.dto.AvaliacaoFinanceiraResponse;
import br.com.zupacademy.joao.propostas.controller.proposta.dto.PropostaRequest;
import br.com.zupacademy.joao.propostas.model.Proposta;
import br.com.zupacademy.joao.propostas.repository.PropostaRepository;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
public class PropostaController {

    private final Logger logger = LoggerFactory.getLogger(PropostaController.class);

    @Autowired
    private PropostaRepository repository;

    @Autowired
    private AvaliacaoFinaneiraClient client;

    @PostMapping("/proposta")
    private ResponseEntity<?> cadastrarProposta(@Valid @RequestBody PropostaRequest request, UriComponentsBuilder builder) {
        Optional<Proposta> possivelProposta = repository.findByDocumento(request.getDocumento());

        if(possivelProposta.isPresent()) {
            logger.info("Proposta existente para documento={}", request.getDocumento());

            // O motivo desta mensagem é pelo o fato de não expressar que um documento está incorreto.
            // Isso poderia motiva um hacker a fazer tentativas e erros constantes!
            throw new ApiErroException(HttpStatus.UNPROCESSABLE_ENTITY, "Proposta não cadastrada");
        }

        Proposta proposta = request.toProposta();
        repository.save(proposta);
        logger.info("Proposta criada: proposta={}, salario={}", proposta.getNomeProposta(), proposta.getSalario());

        AvaliacaoFinanceiraResponse response = client.avalia(new AvaliacaoFinanceiraRequest(proposta));

        proposta.atualizarEstadoDaAvaliacao(response);
        repository.save(proposta);

        URI uri = builder.path("/proposta/{id}").buildAndExpand(proposta.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }
}
