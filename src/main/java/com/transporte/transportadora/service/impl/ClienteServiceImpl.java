package com.transporte.transportadora.service.impl;

import com.transporte.transportadora.model.Cliente;
import com.transporte.transportadora.repository.ClienteRepository;
import com.transporte.transportadora.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public Cliente salvarCliente(Cliente cliente) {
        validarCliente(cliente);
        return clienteRepository.save(cliente);
    }

    @Override
    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    @Override
    public Optional<Cliente> buscarPorId(Long id) {
        return clienteRepository.findById(id);
    }

    @Override
    public Cliente atualizarCliente(Long id, Cliente cliente) {
        validarCliente(cliente);
        Optional<Cliente> clienteExistente = clienteRepository.findById(id);
        if (clienteExistente.isPresent()) {
            Cliente clienteAtualizado = clienteExistente.get();
            clienteAtualizado.setEndereco(cliente.getEndereco());
            clienteAtualizado.setTelefone(cliente.getTelefone());
            clienteAtualizado.setDataInsc(cliente.getDataInsc());
            clienteAtualizado.setTipoCliente(cliente.getTipoCliente());
            if (cliente.getPessoaFisica() != null) {
                clienteAtualizado.setPessoaFisica(cliente.getPessoaFisica());
            }
            if (cliente.getPessoaJuridica() != null) {
                clienteAtualizado.setPessoaJuridica(cliente.getPessoaJuridica());
            }
            return clienteRepository.save(clienteAtualizado);
        } else {
            throw new RuntimeException("Cliente não encontrado com o ID: " + id);
        }
    }

    @Override
    public void deletarCliente(Long id) {
        if (clienteRepository.existsById(id)) {
            clienteRepository.deleteById(id);
        } else {
            throw new RuntimeException("Cliente não encontrado com o ID: " + id);
        }
    }

    private void validarCliente(Cliente cliente) {
        if (cliente.getTipoCliente() == null) {
            throw new IllegalArgumentException("Tipo de cliente não pode ser nulo.");
        }
        if (cliente.getTipoCliente().equals(com.transporte.transportadora.model.TipoCliente.PESSOA_FISICA)) {
            if (cliente.getPessoaFisica() == null || cliente.getPessoaFisica().getNomeCli() == null) {
                throw new IllegalArgumentException("Nome da pessoa física não pode ser nulo.");
            }
        } else if (cliente.getTipoCliente().equals(com.transporte.transportadora.model.TipoCliente.PESSOA_JURIDICA)) {
            if (cliente.getPessoaJuridica() == null || cliente.getPessoaJuridica().getRazaoSocial() == null) {
                throw new IllegalArgumentException("Razão social da pessoa jurídica não pode ser nula.");
            }
        }
    }
}
