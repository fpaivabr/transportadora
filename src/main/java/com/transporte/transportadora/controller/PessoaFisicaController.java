package com.transporte.transportadora.controller;

import com.transporte.transportadora.model.PessoaFisica;
import com.transporte.transportadora.repository.PessoaFisicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pessoas-fisicas")
public class PessoaFisicaController {

    @Autowired
    private PessoaFisicaRepository pessoaFisicaRepository;

    @GetMapping
    public ResponseEntity<List<PessoaFisica>> listarTodas() {
        List<PessoaFisica> pessoasFisicas = pessoaFisicaRepository.findAll();
        return ResponseEntity.ok(pessoasFisicas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PessoaFisica> buscarPorId(@PathVariable Long id) {
        return pessoaFisicaRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @PostMapping
    public ResponseEntity<PessoaFisica> criar(@RequestBody PessoaFisica pessoaFisica) {
        PessoaFisica novaPessoaFisica = pessoaFisicaRepository.save(pessoaFisica);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaPessoaFisica);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PessoaFisica> atualizar(@PathVariable Long id, @RequestBody PessoaFisica pessoaAtualizada) {
        return pessoaFisicaRepository.findById(id)
                .map(pessoa -> {
                    pessoa.setNomeCli(pessoaAtualizada.getNomeCli());
                    pessoa.setCpf(pessoaAtualizada.getCpf());
                    PessoaFisica atualizada = pessoaFisicaRepository.save(pessoa);
                    return ResponseEntity.ok(atualizada);
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (pessoaFisicaRepository.existsById(id)) {
            pessoaFisicaRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
