package br.com.zupacademy.joao.propostas.repository;

import br.com.zupacademy.joao.propostas.model.Proposta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PropostaRepository extends JpaRepository<Proposta, Long> {
    Optional<Proposta> findByDocumento(String documento);
}
