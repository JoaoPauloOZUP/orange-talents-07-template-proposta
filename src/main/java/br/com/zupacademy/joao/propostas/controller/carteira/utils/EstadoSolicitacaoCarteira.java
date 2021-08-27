package br.com.zupacademy.joao.propostas.controller.carteira.utils;

public enum EstadoSolicitacaoCarteira {
    ASSOCIADA {
        @Override
        public boolean resultado() {
            return true;
        }
    },
    FALHA {
        @Override
        public boolean resultado() {
            return false;
        }
    },
    ;

    public abstract boolean resultado();
}
