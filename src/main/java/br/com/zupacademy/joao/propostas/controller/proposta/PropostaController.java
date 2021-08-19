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
import org.springframework.transaction.support.TransactionTemplate;
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

    @Autowired
    private TransactionTemplate transaction;

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
        transaction.execute(status -> repository.save(proposta));
        logger.info("Proposta criada: proposta={}, salario={}", proposta.getNomeProposta(), proposta.getSalario());

        try {
            AvaliacaoFinanceiraResponse response = client.avalia(new AvaliacaoFinanceiraRequest(proposta));
            proposta.atualizarEstadoDaAvaliacao(response);
        } catch (FeignException.FeignClientException exception) {
            logger.error("Erro na comunicação com API externa. ERRO={}, STATUS={}", exception.getMessage(), exception.status());

            throw new ApiErroException(HttpStatus.UNPROCESSABLE_ENTITY, "Algo deu errado. Tente novamente mais tarde");
        }

        // Na main, o contexto de transação defautlt ativado pelo o repository do JPA está desativado.
        // Desta maneira, abro uma conexão com BD toda vez que preciso e não seguro uma conexão por método
        transaction.execute(status -> repository.save(proposta));

        URI uri = builder.path("/proposta/{id}").buildAndExpand(proposta.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }
}
