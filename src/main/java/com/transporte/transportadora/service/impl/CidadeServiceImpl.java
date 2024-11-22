package com.transporte.transportadora.service.impl;

import com.transporte.transportadora.model.Cidade;
import com.transporte.transportadora.repository.CidadeRepository;
import com.transporte.transportadora.repository.EstadoRepository;
import com.transporte.transportadora.service.CidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CidadeServiceImpl implements CidadeService {


    private final CidadeRepository cidadeRepository;

    public CidadeServiceImpl(CidadeRepository cidadeRepository){
        this.cidadeRepository = cidadeRepository;
    }

    @Override
    public Cidade salvarCidade(Cidade cidade) {
        validarCidade(cidade);
        return cidadeRepository.save(cidade);
    }

    @Override
    public List<Cidade> listarTodas() {
        return cidadeRepository.findAll();
    }

    @Override
    public Optional<Cidade> buscarPorId(Long id) {
        return cidadeRepository.findById(id);
    }

    @Override
    public Cidade atualizarCidade(Long id, Cidade cidade) {
        validarCidade(cidade);
        Optional<Cidade> cidadeExistente = cidadeRepository.findById(id);
        if (cidadeExistente.isPresent()) {
            Cidade cidadeAtualizada = cidadeExistente.get();
            cidadeAtualizada.setNome(cidade.getNome());
            cidadeAtualizada.setEstado(cidade.getEstado());
            cidadeAtualizada.setPrecoUnitPeso(cidade.getPrecoUnitPeso());
            cidadeAtualizada.setPrecoUnitValor(cidade.getPrecoUnitValor());
            return cidadeRepository.save(cidadeAtualizada);
        } else {
            throw new RuntimeException("Cidade não encontrada com o ID: " + id);
        }
    }

    @Override
    public void deletarCidade(Long id) {
        if (cidadeRepository.existsById(id)) {
            cidadeRepository.deleteById(id);
        } else {
            throw new RuntimeException("Cidade não encontrada com o ID: " + id);
        }
    }

    private void validarCidade(Cidade cidade) {
        if (cidade.getNome() == null || cidade.getNome().isEmpty()) {
            throw new IllegalArgumentException("O nome da cidade não pode ser vazio.");
        }
        if (cidade.getEstado() == null) {
            throw new IllegalArgumentException("A cidade deve estar associada a um estado.");
        }
    }
}

