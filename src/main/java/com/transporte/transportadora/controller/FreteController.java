package com.transporte.transportadora.controller;

import com.transporte.transportadora.model.Frete;
import com.transporte.transportadora.repository.FreteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fretes")
public class FreteController {

    @Autowired
    private FreteRepository freteRepository;

    @GetMapping
    public ResponseEntity<List<Frete>> listarTodos() {
        List<Frete> fretes = freteRepository.findAll();
        return ResponseEntity.ok(fretes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Frete> buscarPorId(@PathVariable Long id) {
        return freteRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @PostMapping
    public ResponseEntity<Frete> criar(@RequestBody Frete frete) {
        Frete novoFrete = freteRepository.save(frete);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoFrete);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Frete> atualizar(@PathVariable Long id, @RequestBody Frete freteAtualizado) {
        return freteRepository.findById(id)
                .map(frete -> {
                    frete.setDataFrete(freteAtualizado.getDataFrete());
                    frete.setPeso(freteAtualizado.getPeso());
                    frete.setValor(freteAtualizado.getValor());
                    frete.setIcms(freteAtualizado.getIcms());
                    frete.setPedagio(freteAtualizado.getPedagio());
                    frete.setRemetente(freteAtualizado.getRemetente());
                    frete.setDestinatario(freteAtualizado.getDestinatario());
                    frete.setOrigem(freteAtualizado.getOrigem());
                    frete.setDestino(freteAtualizado.getDestino());
                    Frete atualizado = freteRepository.save(frete);
                    return ResponseEntity.ok(atualizado);
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (freteRepository.existsById(id)) {
            freteRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
