package com.transporte.transportadora.service;

import com.transporte.transportadora.model.Cidade;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface CidadeService {
    Cidade salvarCidade(Cidade cidade);
    List<Cidade> listarTodas();
    Optional<Cidade> buscarPorId(Long id);
    Cidade atualizarCidade(Long id, Cidade cidade);
    void deletarCidade(Long id);
}
