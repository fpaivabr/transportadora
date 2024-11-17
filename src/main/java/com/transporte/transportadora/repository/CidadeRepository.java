package com.transporte.transportadora.repository;

import com.transporte.transportadora.model.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface CidadeRepository extends JpaRepository<Cidade, Long> {
}
