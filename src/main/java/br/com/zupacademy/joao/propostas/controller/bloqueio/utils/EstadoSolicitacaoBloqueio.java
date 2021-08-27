package br.com.zupacademy.joao.propostas.controller.bloqueio.utils;

public enum EstadoSolicitacaoBloqueio {
    FALHA {
        @Override
        public EstadoSolicitacaoBloqueio resultado() {
            return FALHA;
        }
    },
    BLOQUEADO
            {
        @Override
        public EstadoSolicitacaoBloqueio resultado() {
            return BLOQUEADO;
        }
    },
    ;

    public abstract EstadoSolicitacaoBloqueio resultado();
}
