package br.com.zupacademy.joao.propostas.controller.biometria.dto;

import br.com.zupacademy.joao.propostas.model.Biometria;
import br.com.zupacademy.joao.propostas.model.Cartao;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class BiometiaRequest {

    @NotNull(message = "Não contém o print da biometria")
    private MultipartFile biometria;

    public void setMultipartFile(@NotNull MultipartFile biometria) {
        this.biometria = biometria;
    }

    public Biometria toBiometria(Cartao cartao) throws IOException {
        // As string são gigantes e como é apenas um cenário para simular algo, limitei a 64.
        String imagem64 = Base64.getEncoder().encodeToString(biometria.getBytes()).substring(0, 64);

        Biometria biometria = new Biometria(imagem64, cartao);

        return biometria;
    }
}
