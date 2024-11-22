package com.transporte.transportadora.repository;

import com.transporte.transportadora.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    @Query(nativeQuery = true, value = "select * from CLIENTE c " +
            "   left join PESSOA_FISICA pf on pf.COD_CLI = c.COD_CLI " +
            "   left join PESSOA_JURIDICA pj on pj.COD_CLI = c.COD_CLI " +
            " WHERE pf.CPF = :documento OR pj.CNPJ = :documento")
    Optional<Cliente> findByDocumento(String documento);
}
