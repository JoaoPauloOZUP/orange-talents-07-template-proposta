package br.com.zupacademy.joao.propostas.repository;

import br.com.zupacademy.joao.propostas.model.Carteira;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarteiraRepository extends JpaRepository<Carteira, Long> {
}
