package br.com.zupacademy.joao.propostas.repository;

import br.com.zupacademy.joao.propostas.model.Proposta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PropostaRepository extends JpaRepository<Proposta, Long> {
    Optional<Proposta> findByDocumento(String documento);

    @Query(value = "SELECT * FROM proposta p WHERE p.estado_cartao = :estado_cartao LIMIT 100", nativeQuery = true)
    List<Proposta> findByEstadoCartao(@Param("estado_cartao") String estadoCartao);
}
