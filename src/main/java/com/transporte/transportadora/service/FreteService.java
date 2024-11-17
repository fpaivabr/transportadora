package com.transporte.transportadora.service;

import com.transporte.transportadora.model.Frete;
import java.util.List;
import java.util.Optional;

public interface FreteService {

    Frete salvarFrete(Frete frete);

    List<Frete> listarTodos();

    Optional<Frete> buscarPorId(Long id);

    Frete atualizarFrete(Long id, Frete frete);

    void deletarFrete(Long id);
}
