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

public class AvisoViagemRequest {

    @NotBlank
    private String destino;

    @NotNull
    @Future
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate validoAte;

    @Deprecated
    private AvisoViagemRequest() {
    }

    @JsonCreator
    public AvisoViagemRequest(@NotBlank String destino,
                              @NotNull @Future LocalDate validoAte) {

        this.destino = destino;
        this.validoAte = validoAte;
    }

    public AvisoViagem toAvisoViagem(HttpServletRequest httpRequest, Cartao cartao) {
        return new AvisoViagem(destino, validoAte, httpRequest, cartao);
    }

    public String getDestino() {
        return destino;
    }

    public LocalDate getValidoAte() {
        return validoAte;
    }
}
