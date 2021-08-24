package br.com.zupacademy.joao.mercadolivre.config.security.dto.response;

public class TokenResponse {

    private String token;

    private String bearer;

    public TokenResponse(String token, String bearer) {
        this.token = token;
        this.bearer = bearer;
    }

    public String getToken() {
        return token;
    }

    public String getBearer() {
        return bearer;
    }
}
