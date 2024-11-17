package com.transporte.transportadora.service;

import com.transporte.transportadora.model.Estado;
import java.util.List;
import java.util.Optional;

public interface EstadoService {
    Estado salvarEstado(Estado estado);
    List<Estado> listarTodos();
    Optional<Estado> buscarPorId(Long id);
    Estado atualizarEstado(Long id, Estado estado);
    void deletarEstado(Long id);
}
