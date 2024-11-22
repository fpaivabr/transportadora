package com.transporte.transportadora.service;

import com.transporte.transportadora.model.PessoaFisica;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface PessoaFisicaService {
    PessoaFisica salvarPessoaFisica(PessoaFisica pessoaFisica);
    List<PessoaFisica> listarTodos();
    Optional<PessoaFisica> buscarPorId(Long id);
    PessoaFisica atualizarPessoaFisica(Long id, PessoaFisica pessoaFisica);
    void deletarPessoaFisica(Long id);
}
