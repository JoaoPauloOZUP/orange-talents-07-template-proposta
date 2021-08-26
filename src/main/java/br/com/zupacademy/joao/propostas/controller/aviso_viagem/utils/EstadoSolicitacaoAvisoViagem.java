package br.com.zupacademy.joao.propostas.controller.aviso_viagem.utils;

public enum EstadoSolicitacaoAvisoViagem {
    CRIADO {
        @Override
        public EstadoAviso resultado() {
            return EstadoAviso.CONCLUIDO;
        }
    },
    ;

    public abstract EstadoAviso resultado();
}
