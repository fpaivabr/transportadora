package com.transporte.transportadora.repository;

import com.transporte.transportadora.model.PessoaJuridica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PessoaJuridicaRepository extends JpaRepository<PessoaJuridica, Long> {
    @Query(nativeQuery = true, value = "" +
            "SELECT " +
            "    f.NUM_CONHEC AS numero_conhecimento, " +
            "    f.DATA_FRETE AS data_frete, " +
            "    pj.RAZAO_SOCIAL AS razao_social, " +
            "    pj.CNPJ AS cnpj, " +
            "    pf.NOME_CLI AS representante " +
            "FROM FRETE f " +
            "         JOIN CLIENTE remetente ON f.REMETENTE = remetente.COD_CLI " +
            "         JOIN PESSOA_JURIDICA pj ON remetente.COD_CLI = pj.COD_CLI " +
            "         JOIN CLIENTE destinatario ON f.DESTINATARIO = destinatario.COD_CLI " +
            "         JOIN PESSOA_FISICA pf ON destinatario.COD_CLI = pf.COD_CLI " +
            "WHERE MONTH(f.DATA_FRETE) = :mes " +
            "  AND YEAR(f.DATA_FRETE) = :ano " +
            "ORDER BY f.DATA_FRETE")
    List<Object[]> buscarFretesPorPessoaJuridica( int mes,  int ano);

}
