package br.com.zupacademy.joao.propostas.controller.bloqueio.utils;

public enum EstadoSolicitacaoBloqueio {
    FALHA {
        @Override
        public EstadoBloqueio resultado() {
            return EstadoBloqueio.EM_ESPERA;
        }
    },
    BLOQUEADO {
        @Override
        public EstadoBloqueio resultado() {
            return EstadoBloqueio.EFETIVADO;
        }
    },
    ;

    public abstract EstadoBloqueio resultado();
}
