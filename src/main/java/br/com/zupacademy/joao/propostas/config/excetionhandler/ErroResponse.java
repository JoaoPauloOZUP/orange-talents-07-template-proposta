package br.com.zupacademy.joao.propostas.config.excetionhandler;

public class ErroResponse {

    private String campo;

    private String erro;

    public ErroResponse(String campo, String erro) {
        this.campo = campo;
        this.erro = erro;
    }

    public String getCampo() {
        return campo;
    }

    public String getErro() {
        return erro;
    }
}
