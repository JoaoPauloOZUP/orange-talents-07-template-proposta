package br.com.zupacademy.joao.propostas.repository;

import br.com.zupacademy.joao.propostas.model.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartaoRepository extends JpaRepository<Cartao, Long> {
    Optional<Cartao> findByNumeroCartao(String numeroCartao);
}
