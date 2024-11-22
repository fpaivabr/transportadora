package com.transporte.transportadora.repository;

import com.transporte.transportadora.model.Cliente;
import com.transporte.transportadora.model.Frete;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FreteRepository extends JpaRepository<Frete, Long> {
    List<Frete> findByRemetente(Cliente remetente);

    Optional<Frete> findByNumConhec(Long numConhec);
}

