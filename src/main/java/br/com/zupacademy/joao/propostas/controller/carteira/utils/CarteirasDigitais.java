package br.com.zupacademy.joao.propostas.controller.carteira.utils;

public enum CarteirasDigitais {
    PAYPAL {
        @Override
        public CarteirasDigitais obterCarteira() {
            return PAYPAL;
        }
    },
    ;

    public abstract CarteirasDigitais obterCarteira();
}
