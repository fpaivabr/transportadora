package com.transporte.transportadora.repository;

import com.transporte.transportadora.model.Cliente;
import com.transporte.transportadora.model.Frete;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FreteRepository extends JpaRepository<Frete, Long> {
    List<Frete> findByRemetente(Cliente remetente);


    @Query(nativeQuery = true,value = "" +
            "SELECT * from FRETE f where f.num_conhec = : numConhec")
    Optional<Frete> findByNumConhec(String numConhec);

    @Query(nativeQuery = true, value =
            "SELECT " +
            "    c.NOME AS cidade, " +
            "    e.NOME AS estado, " +
            "    COUNT(f.NUM_CONHEC) AS quantidade_de_fretes, " +
            "    SUM(f.VALOR) AS valor_total_arrecadado " +
            "FROM FRETE f " +
            "         JOIN CIDADE c ON f.CODIGO_DESTINO = c.ID " +
            "         JOIN ESTADO e ON c.ESTADO_ID = e.ID " +
            "WHERE e.NOME = :estado " +
            "  AND YEAR(f.DATA_FRETE) = 2024 " +
            "GROUP BY c.NOME, e.NOME " +
            "ORDER BY e.NOME, c.NOME")
    List<Object[]> buscarArrecadacaoPorEstado(String estado);

}

