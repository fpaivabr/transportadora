package com.transporte.transportadora.service.impl;

import com.transporte.transportadora.model.Frete;
import com.transporte.transportadora.repository.FreteRepository;
import com.transporte.transportadora.service.FreteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class FreteServiceImpl implements FreteService {

    @Autowired
    private FreteRepository freteRepository;

    @Override
    public Frete salvarFrete(Frete frete) {
        validarFrete(frete);
        return freteRepository.save(frete);
    }

    @Override
    public List<Frete> listarTodos() {
        return freteRepository.findAll();
    }

    @Override
    public Optional<Frete> buscarPorId(Long id) {
        return freteRepository.findById(id);
    }

    @Override
    public Frete atualizarFrete(Long id, Frete frete) {
        validarFrete(frete);
        Optional<Frete> freteExistente = freteRepository.findById(id);
        if (freteExistente.isPresent()) {
            Frete freteAtualizado = freteExistente.get();
            freteAtualizado.setDataFrete(frete.getDataFrete());
            freteAtualizado.setPeso(frete.getPeso());
            freteAtualizado.setValor(frete.getValor());
            freteAtualizado.setIcms(frete.getIcms());
            freteAtualizado.setPedagio(frete.getPedagio());
            freteAtualizado.setRemetente(frete.getRemetente());
            freteAtualizado.setDestinatario(frete.getDestinatario());
            freteAtualizado.setOrigem(frete.getOrigem());
            freteAtualizado.setDestino(frete.getDestino());
            return freteRepository.save(freteAtualizado);
        } else {
            throw new RuntimeException("Frete não encontrado com o ID: " + id);
        }
    }

    @Override
    public void deletarFrete(Long id) {
        if (freteRepository.existsById(id)) {
            freteRepository.deleteById(id);
        } else {
            throw new RuntimeException("Frete não encontrado com o ID: " + id);
        }
    }

    /**
     * Método para validar os campos do frete.
     *
     * @param frete Frete a ser validado
     */
    private void validarFrete(Frete frete) {
        if (frete.getDataFrete() == null || frete.getDataFrete().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("A data do frete não pode ser anterior à data de hoje.");
        }
        if (frete.getPeso() == null || frete.getPeso() <= 0) {
            throw new IllegalArgumentException("Peso inválido. O peso deve ser maior que zero.");
        }
        if (frete.getValor() == null || frete.getValor() <= 0) {
            throw new IllegalArgumentException("Valor inválido. O valor deve ser maior que zero.");
        }
        if (frete.getIcms() == null || frete.getIcms() < 0) {
            throw new IllegalArgumentException("ICMS inválido. O ICMS não pode ser negativo.");
        }
        if (frete.getPedagio() == null || frete.getPedagio() < 0) {
            throw new IllegalArgumentException("Pedágio inválido. O pedágio não pode ser negativo.");
        }
        if (frete.getRemetente() == null) {
            throw new IllegalArgumentException("Remetente não pode ser nulo.");
        }
        if (frete.getDestinatario() == null) {
            throw new IllegalArgumentException("Destinatário não pode ser nulo.");
        }
        if (frete.getOrigem() == null) {
            throw new IllegalArgumentException("Origem não pode ser nula.");
        }
        if (frete.getDestino() == null) {
            throw new IllegalArgumentException("Destino não pode ser nulo.");
        }
    }
}
