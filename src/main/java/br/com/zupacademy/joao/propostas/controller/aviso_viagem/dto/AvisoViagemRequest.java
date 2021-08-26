package br.com.zupacademy.joao.propostas.controller.aviso_viagem.dto;

import br.com.zupacademy.joao.propostas.model.AvisoViagem;
import br.com.zupacademy.joao.propostas.model.Cartao;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Date;

public class AvisoViagemRequest {

    @NotBlank
    private String destino;

    @NotNull
    @Future
    @JsonFormat(pattern="dd-MM-yyyy")
    private LocalDate dataTermino;

    @Deprecated
    private AvisoViagemRequest() {
    }

    @JsonCreator
    public AvisoViagemRequest(@NotBlank String destino,
                              @NotNull @Future LocalDate dataTermino) {

        this.destino = destino;
        this.dataTermino = dataTermino;
    }

    public AvisoViagem toAvisoViagem(HttpServletRequest httpRequest, Cartao cartao) {
        return new AvisoViagem(destino, dataTermino, httpRequest, cartao);
    }
}
