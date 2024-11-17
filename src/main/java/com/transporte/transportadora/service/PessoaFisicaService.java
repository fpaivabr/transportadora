package com.transporte.transportadora.service;

import com.transporte.transportadora.model.PessoaFisica;
import java.util.List;
import java.util.Optional;

public interface PessoaFisicaService {
    PessoaFisica salvarPessoaFisica(PessoaFisica pessoaFisica);
    List<PessoaFisica> listarTodos();
    Optional<PessoaFisica> buscarPorId(Long id);
    PessoaFisica atualizarPessoaFisica(Long id, PessoaFisica pessoaFisica);
    void deletarPessoaFisica(Long id);
}
