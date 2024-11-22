package com.transporte.transportadora.service;

import com.transporte.transportadora.model.PessoaJuridica;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface PessoaJuridicaService {
    PessoaJuridica salvarPessoaJuridica(PessoaJuridica pessoaJuridica);
    List<PessoaJuridica> listarTodos();
    Optional<PessoaJuridica> buscarPorId(Long id);
    PessoaJuridica atualizarPessoaJuridica(Long id, PessoaJuridica pessoaJuridica);
    void deletarPessoaJuridica(Long id);
}
