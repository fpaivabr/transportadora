package com.transporte.transportadora.service;

import com.transporte.transportadora.model.Cliente;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ClienteService {
    Cliente salvarCliente(Cliente cliente);
    List<Cliente> listarTodos();
    Optional<Cliente> buscarPorId(Long id);
    Cliente atualizarCliente(Long id, Cliente cliente);
    void deletarCliente(Long id);
}
