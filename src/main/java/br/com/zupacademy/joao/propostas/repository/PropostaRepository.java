package br.com.zupacademy.joao.propostas.repository;

import br.com.zupacademy.joao.propostas.model.Proposta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropostaRepository extends JpaRepository<Proposta, Long> {
}
