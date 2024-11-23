package com.transporte.transportadora.service.impl;

import com.transporte.transportadora.model.Estado;
import com.transporte.transportadora.repository.EstadoRepository;
import com.transporte.transportadora.service.EstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EstadoServiceImpl implements EstadoService {

    private final EstadoRepository estadoRepository;

    public EstadoServiceImpl(EstadoRepository estadoRepository){
        this.estadoRepository = estadoRepository;
    }

    @Override
    public Estado salvarEstado(Estado estado) {
        validarEstado(estado);
        return estadoRepository.save(estado);
    }

    @Override
    public List<Estado> listarTodos() {
        return estadoRepository.findAll();
    }

    @Override
    public Optional<Estado> buscarPorId(Long id) {
        return estadoRepository.findById(id);
    }

    @Override
    public Estado atualizarEstado(Long id, Estado estado) {
        validarEstado(estado);
        Optional<Estado> estadoExistente = estadoRepository.findById(id);
        if (estadoExistente.isPresent()) {
            Estado estadoAtualizado = estadoExistente.get();
            estadoAtualizado.setNome(estado.getNome());
            estadoAtualizado.setIcmsLocal(estado.getIcmsLocal());
            estadoAtualizado.setIcmsOutroUf(estado.getIcmsOutroUf());
            return estadoRepository.save(estadoAtualizado);
        } else {
            throw new RuntimeException("Estado não encontrado com o ID: " + id);
        }
    }

    @Override
    public void deletarEstado(Long id) {
        if (estadoRepository.existsById(id)) {
            estadoRepository.deleteById(id);
        } else {
            throw new RuntimeException("Estado não encontrado com o ID: " + id);
        }
    }

    private void validarEstado(Estado estado) {
        if (estado.getNome() == null || estado.getNome().isEmpty()) {
            throw new IllegalArgumentException("Nome do estado não pode ser nulo ou vazio.");
        }
        if (estado.getIcmsLocal() == null || estado.getIcmsLocal() < 0) {
            throw new IllegalArgumentException("ICMS local inválido.");
        }
        if (estado.getIcmsOutroUf() == null || estado.getIcmsOutroUf() < 0) {
            throw new IllegalArgumentException("ICMS de outro estado inválido.");
        }
    }
}