package com.transporte.transportadora.repository;

import com.transporte.transportadora.model.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Long> {
    Optional<Cidade> findByNome(String nome);

    @Query(nativeQuery = true, value = "" +
            "SELECT " +
            "    e.NOME AS estado, " +
            "    c.NOME AS cidade, " +
            "    AVG(origem.total) AS media_fretes_origem, " +
            "    AVG(destino.total) AS media_fretes_destino " +
            "FROM ESTADO e " +
            "         JOIN CIDADE c ON e.ID = c.ESTADO_ID " +
            "         LEFT JOIN ( " +
            "    SELECT " +
            "        CODIGO_ORIGEM AS cidade_id, " +
            "        COUNT(*) AS total " +
            "    FROM FRETE " +
            "    GROUP BY CODIGO_ORIGEM " +
            ") origem ON c.ID = origem.cidade_id " +
            "         LEFT JOIN ( " +
            "    SELECT " +
            "        CODIGO_DESTINO AS cidade_id, " +
            "        COUNT(*) AS total " +
            "    FROM FRETE " +
            "    GROUP BY CODIGO_DESTINO " +
            ") destino ON c.ID = destino.cidade_id " +
            "WHERE e.NOME = :nomeEstado " +
            "GROUP BY e.NOME, c.NOME " +
            "ORDER BY e.NOME, c.NOME")
    List<Object[]> buscarMediaFretesPorEstado(@Param("nomeEstado") String nomeEstado);

}
