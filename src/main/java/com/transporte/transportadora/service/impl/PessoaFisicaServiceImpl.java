package com.transporte.transportadora.service.impl;

import com.transporte.transportadora.model.PessoaFisica;
import com.transporte.transportadora.repository.PessoaFisicaRepository;
import com.transporte.transportadora.service.PessoaFisicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PessoaFisicaServiceImpl implements PessoaFisicaService {


    private final PessoaFisicaRepository pessoaFisicaRepository;

    public PessoaFisicaServiceImpl(PessoaFisicaRepository pessoaFisicaRepository){
        this.pessoaFisicaRepository = pessoaFisicaRepository;
    }

    @Override
    public PessoaFisica salvarPessoaFisica(PessoaFisica pessoaFisica) {
        validarPessoaFisica(pessoaFisica);
        return pessoaFisicaRepository.save(pessoaFisica);
    }

    @Override
    public List<PessoaFisica> listarTodos() {
        return pessoaFisicaRepository.findAll();
    }


    @Override
    public Optional<PessoaFisica> buscarPorId(Long id) {
        return pessoaFisicaRepository.findById(id);
    }

    @Override
    public PessoaFisica atualizarPessoaFisica(Long id, PessoaFisica pessoaFisica) {
        validarPessoaFisica(pessoaFisica);
        Optional<PessoaFisica> pessoaExistente = pessoaFisicaRepository.findById(id);
        if (pessoaExistente.isPresent()) {
            PessoaFisica pessoaAtualizada = pessoaExistente.get();
            pessoaAtualizada.setNomeCli(pessoaFisica.getNomeCli());
            pessoaAtualizada.setCpf(pessoaFisica.getCpf());
            return pessoaFisicaRepository.save(pessoaAtualizada);
        } else {
            throw new RuntimeException("Pessoa Física não encontrada com o ID: " + id);
        }
    }

    @Override
    public void deletarPessoaFisica(Long id) {
        if (pessoaFisicaRepository.existsById(id)) {
            pessoaFisicaRepository.deleteById(id);
        } else {
            throw new RuntimeException("Pessoa Física não encontrada com o ID: " + id);
        }
    }

    private void validarPessoaFisica(PessoaFisica pessoaFisica) {
        if (pessoaFisica.getNomeCli() == null || pessoaFisica.getNomeCli().isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser vazio.");
        }
        if (pessoaFisica.getCpf() == null || pessoaFisica.getCpf().length() != 11) {
            throw new IllegalArgumentException("CPF inválido.");
        }
    }
}
