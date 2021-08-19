package br.com.zupacademy.joao.propostas.controller.proposta.utils;

public enum EstadoSolicitacao {
    COM_RESTRICAO {
        @Override
        public EstadoAvaliacao resultado() {
            return EstadoAvaliacao.NAO_ELEGIVEL;
        }
    },
    SEM_RESTRICAO{
        @Override
        public EstadoAvaliacao resultado() {
            return EstadoAvaliacao.ELEGIVEL;
        }
    },
    ;

    public abstract EstadoAvaliacao resultado();
}
