package br.com.zupacademy.joao.propostas.controller.proposta.utils;

public enum EstadoSolicitacaoDaProposta {
    COM_RESTRICAO {
        @Override
        public EstadoAvaliacaoDaProposta resultado() {
            return EstadoAvaliacaoDaProposta.NAO_ELEGIVEL;
        }
    },
    SEM_RESTRICAO{
        @Override
        public EstadoAvaliacaoDaProposta resultado() {
            return EstadoAvaliacaoDaProposta.ELEGIVEL;
        }
    },
    ;

    public abstract EstadoAvaliacaoDaProposta resultado();
}
