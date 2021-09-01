package br.com.zupacademy.joao.propostas.controller.proposta;

import br.com.zupacademy.joao.propostas.controller.proposta.clients.AvaliacaoFinaneiraClient;
import br.com.zupacademy.joao.propostas.config.exception.ApiErroException;
import br.com.zupacademy.joao.propostas.controller.proposta.clients.CartaoClient;
import br.com.zupacademy.joao.propostas.controller.proposta.clients.dto.avaliacaofinanceira.AvaliacaoFinanceiraRequest;
import br.com.zupacademy.joao.propostas.controller.proposta.clients.dto.avaliacaofinanceira.AvaliacaoFinanceiraResponse;
import br.com.zupacademy.joao.propostas.controller.proposta.dto.PropostaRequest;
import br.com.zupacademy.joao.propostas.controller.proposta.dto.PropostaResponse;
import br.com.zupacademy.joao.propostas.controller.proposta.utils.DocumentoEncode;
import br.com.zupacademy.joao.propostas.model.Proposta;
import br.com.zupacademy.joao.propostas.repository.PropostaRepository;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.bind.annotation.*;
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
    private AvaliacaoFinaneiraClient clientFinanceira;

    @Autowired
    private TransactionTemplate transaction;

    @Autowired
    private CartaoClient clientCartao;

    @PostMapping("/proposta")
    private ResponseEntity<?> cadastrarProposta(@Valid @RequestBody PropostaRequest request, UriComponentsBuilder builder) {

        Optional<Proposta> possivelProposta = consultaProposta(request, repository);

        if(possivelProposta.isPresent()) {
            logger.info("Proposta existente para DOCUMENTO={}", request.getDocumento());

            // O motivo desta mensagem é pelo o fato de não expressar que um documento está incorreto.
            // Isso poderia motiva um hacker a fazer tentativas e erros constantes!
            throw new ApiErroException(HttpStatus.UNPROCESSABLE_ENTITY, "Proposta não cadastrada");
        }


        Optional<Proposta> propostaProtegida = protegendoProposta(request);
        if(propostaProtegida.isEmpty()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Aconteceu um erro inesperado. Tente mais tarde");
        }

        Proposta proposta = propostaProtegida.get();
        transaction.execute(status -> repository.save(proposta));
        logger.info("Proposta criada: PROPOSTA={}, SALARIO={}", proposta.getId(), proposta.getSalario());

        try {
            AvaliacaoFinanceiraResponse response = clientFinanceira.avalia(new AvaliacaoFinanceiraRequest(proposta));
            proposta.atualizarEstadoDaAvaliacao(response);
        } catch (FeignException.FeignClientException exception) {
            logger.error("Erro na comunicação com API externa. STATUS={}, ERRO={}", exception.status(), exception.getMessage());

            throw new ApiErroException(HttpStatus.SERVICE_UNAVAILABLE, "Algo deu errado. Tente novamente mais tarde");
        }

        // Na main, o contexto de transação defautlt ativado pelo o repository do JPA está desativado.
        // Desta maneira, abro uma conexão com BD toda vez que preciso e não seguro uma conexão por método
        transaction.execute(status -> repository.save(proposta));

        URI uri = builder.path("/proposta/{id}").buildAndExpand(proposta.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

    @GetMapping("proposta/{id}")
    private ResponseEntity<?> obterProposta(@PathVariable("id") Long idProposta) {
        Optional<Proposta> possivelProposta = repository.findById(idProposta);

        if(possivelProposta.isPresent()) {
            return ResponseEntity.ok(new PropostaResponse(possivelProposta.get()));
        }

        return ResponseEntity.notFound().build();
    }

    private Optional<Proposta> consultaProposta(PropostaRequest request, PropostaRepository repository) {
        try {
            DocumentoEncode documentoEncode = new  DocumentoEncode();
            String documentoEcript = documentoEncode.encodar(request.getDocumento());
            return repository.findByDocumento(documentoEcript);
        } catch (Exception exception) {
            logger.error("Erro ao encriptar documento. ERRO={}", exception.getMessage());
            throw new ApiErroException(HttpStatus.SERVICE_UNAVAILABLE, "Erro inesperado aconteceu. Tente mais tarde");
        }
    }

    private Optional<Proposta> protegendoProposta(PropostaRequest request) {
        try {
            Proposta proposta = request.toProposta();
            return Optional.of(proposta);
        } catch (Exception exception) {
            logger.error("Erro ao encriptar documento. ERRO={}", exception.getMessage());
            return Optional.empty();
        }
    }
}
