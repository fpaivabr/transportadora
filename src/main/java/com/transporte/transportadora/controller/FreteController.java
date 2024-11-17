package com.transporte.transportadora.controller;

import com.transporte.transportadora.model.Frete;
import com.transporte.transportadora.service.FreteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fretes")
public class FreteController {

    @Autowired
    private FreteService freteService;

    @GetMapping
    public ResponseEntity<List<Frete>> listarTodos() {
        List<Frete> fretes = freteService.listarTodos();
        return ResponseEntity.ok(fretes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Frete> buscarPorId(@PathVariable Long id) {
        return freteService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<Frete> criar(@RequestBody Frete frete) {
        Frete novoFrete = freteService.salvarFrete(frete);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoFrete);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Frete> atualizar(@PathVariable Long id, @RequestBody Frete freteAtualizado) {
        try {
            Frete frete = freteService.atualizarFrete(id, freteAtualizado);
            return ResponseEntity.ok(frete);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        try {
            freteService.deletarFrete(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
