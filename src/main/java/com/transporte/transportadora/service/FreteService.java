package com.transporte.transportadora.service;

import com.transporte.transportadora.model.Cliente;
import com.transporte.transportadora.model.Frete;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface FreteService {

    Frete salvarFrete(Frete frete);

    List<Frete> listarTodos();

    Optional<Frete> buscarPorId(Long id);

    Frete atualizarFrete(Long id, Frete frete);

    void deletarFrete(Long id);

    List<Frete> buscarFretesPorRemetente(Cliente remetente);
}
