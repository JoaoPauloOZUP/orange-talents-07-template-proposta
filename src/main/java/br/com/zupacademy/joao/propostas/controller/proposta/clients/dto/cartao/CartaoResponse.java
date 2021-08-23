package br.com.zupacademy.joao.propostas.controller.proposta.clients.dto.cartao;

import br.com.zupacademy.joao.propostas.model.*;
import br.com.zupacademy.joao.propostas.repository.PropostaRepository;

import javax.persistence.EntityManager;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class CartaoResponse {

    private String id;

    private String emitidoEm;

    private String titular;

    private List<BloqueioResponse> bloqueios;

    private List<AvisoViagemResponse> avisos;

    private List<CarteiraResponse> carteiras;

    private List<ParcelaResponse> parcelas;

    private Integer limite;

    private RenegociacaoResponse renegociacao;

    private VencimentoResponse vencimento;

    private String idProposta;

    public CartaoResponse(String id,
                          String emitidoEm,
                          String titular,
                          List<BloqueioResponse> bloqueios,
                          List<AvisoViagemResponse> avisos,
                          List<CarteiraResponse> carteiras,
                          List<ParcelaResponse> parcelas,
                          Integer limite,
                          RenegociacaoResponse renegociacao,
                          VencimentoResponse vencimento,
                          String idProposta) {

        this.id = id;
        this.emitidoEm = emitidoEm;
        this.titular = titular;
        this.bloqueios = bloqueios;
        this.avisos = avisos;
        this.carteiras = carteiras;
        this.parcelas = parcelas;
        this.limite = limite;
        this.renegociacao = renegociacao;
        this.vencimento = vencimento;
        this.idProposta = idProposta;
    }

    public Cartao toCartao(PropostaRepository repository) {
        Proposta proposta = repository.findById(Long.parseLong(idProposta)).get();
        SimpleDateFormat format = new SimpleDateFormat();

        return new Cartao(id, LocalDateTime.parse(emitidoEm), titular, limite, proposta);
    }
}
