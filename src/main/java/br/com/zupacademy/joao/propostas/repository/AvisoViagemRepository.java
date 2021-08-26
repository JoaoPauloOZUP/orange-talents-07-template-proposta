package br.com.zupacademy.joao.propostas.repository;

import br.com.zupacademy.joao.propostas.controller.aviso_viagem.utils.EstadoAviso;
import br.com.zupacademy.joao.propostas.model.AvisoViagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AvisoViagemRepository extends JpaRepository<AvisoViagem, Long> {

    @Query(value = "SELECT * FROM aviso_viagem a WHERE a.estado_aviso <> :estado_aviso LIMIT 100", nativeQuery = true)
    List<AvisoViagem> findByNotEstadoAviso(@Param("estado_aviso") String estadoAviso);
}
