package com.transporte.transportadora.service.impl;

import com.transporte.transportadora.model.PessoaJuridica;
import com.transporte.transportadora.repository.PessoaJuridicaRepository;
import com.transporte.transportadora.service.PessoaJuridicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PessoaJuridicaServiceImpl implements PessoaJuridicaService {

    @Autowired
    private PessoaJuridicaRepository pessoaJuridicaRepository;

    @Override
    public PessoaJuridica salvarPessoaJuridica(PessoaJuridica pessoaJuridica) {
        validarPessoaJuridica(pessoaJuridica);
        return pessoaJuridicaRepository.save(pessoaJuridica);
    }

    @Override
    public List<PessoaJuridica> listarTodos() {
        return pessoaJuridicaRepository.findAll();
    }

    @Override
    public Optional<PessoaJuridica> buscarPorId(Long id) {
        return pessoaJuridicaRepository.findById(id);
    }

    @Override
    public PessoaJuridica atualizarPessoaJuridica(Long id, PessoaJuridica pessoaJuridica) {
        validarPessoaJuridica(pessoaJuridica);
        return pessoaJuridicaRepository.findById(id).map(pjExistente -> {
            pjExistente.setRazaoSocial(pessoaJuridica.getRazaoSocial());
            pjExistente.setCnpj(pessoaJuridica.getCnpj());
            pjExistente.setInscEstadual(pessoaJuridica.getInscEstadual());
            return pessoaJuridicaRepository.save(pjExistente);
        }).orElseThrow(() -> new RuntimeException("Pessoa Jurídica não encontrada com ID: " + id));
    }

    @Override
    public void deletarPessoaJuridica(Long id) {
        if (pessoaJuridicaRepository.existsById(id)) {
            pessoaJuridicaRepository.deleteById(id);
        } else {
            throw new RuntimeException("Pessoa Jurídica não encontrada com ID: " + id);
        }
    }

    private void validarPessoaJuridica(PessoaJuridica pessoaJuridica) {
        if (pessoaJuridica.getRazaoSocial() == null || pessoaJuridica.getRazaoSocial().isEmpty()) {
            throw new IllegalArgumentException("Razão Social não pode ser vazia.");
        }
        if (pessoaJuridica.getCnpj() == null || pessoaJuridica.getCnpj().isEmpty()) {
            throw new IllegalArgumentException("CNPJ não pode ser vazio.");
        }
        if (pessoaJuridica.getInscEstadual() == null || pessoaJuridica.getInscEstadual().isEmpty()) {
            throw new IllegalArgumentException("Inscrição Estadual não pode ser vazia.");
        }
    }
}


