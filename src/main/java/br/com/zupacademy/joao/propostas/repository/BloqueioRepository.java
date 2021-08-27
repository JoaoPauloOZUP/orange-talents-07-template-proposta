package br.com.zupacademy.joao.propostas.repository;

import br.com.zupacademy.joao.propostas.model.Bloqueio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BloqueioRepository extends JpaRepository<Bloqueio, Long> {
    Optional<Bloqueio> findByCartaoNumeroCartao(String numeroCartao);

    @Query(value = "SELECT * FROM bloqueio b WHERE b.estado_bloqueio = :estado_falha LIMIT 100", nativeQuery = true)
    List<Bloqueio> findByEstadoBloqueio(@Param("estado_falha") String estadoFalha);
}
