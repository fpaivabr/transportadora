package com.transporte.transportadora.controller;

import com.transporte.transportadora.model.Estado;
import com.transporte.transportadora.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estados")
public class EstadoController {

    @Autowired
    private EstadoRepository estadoRepository;

    @GetMapping
    public ResponseEntity<List<Estado>> listarTodos() {
        return ResponseEntity.ok(estadoRepository.findAll());
    }

    @GetMapping("/{uf}")
    public ResponseEntity<Estado> buscarPorUf(@PathVariable Long uf) {
        return estadoRepository.findById(uf)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @PostMapping
    public ResponseEntity<Estado> criar(@RequestBody Estado estado) {
        return ResponseEntity.status(HttpStatus.CREATED).body(estadoRepository.save(estado));
    }

    @PutMapping("/{uf}")
    public ResponseEntity<Estado> atualizar(@PathVariable Long uf, @RequestBody Estado estadoAtualizado) {
        return estadoRepository.findById(uf).map(estado -> {
            estado.setNome(estadoAtualizado.getNome()); // Alterado para 'setNome'
            estado.setIcmsLocal(estadoAtualizado.getIcmsLocal());
            estado.setIcmsOutroUf(estadoAtualizado.getIcmsOutroUf());
            estadoRepository.save(estado);
            return ResponseEntity.ok(estado);
        }).orElse(ResponseEntity.notFound().build());
    }


    @DeleteMapping("/{uf}")
    public ResponseEntity<Void> deletar(@PathVariable Long uf) {
        if (estadoRepository.existsById(uf)) {
            estadoRepository.deleteById(uf);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
