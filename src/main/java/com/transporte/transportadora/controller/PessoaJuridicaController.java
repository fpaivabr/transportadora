package com.transporte.transportadora.controller;

import com.transporte.transportadora.model.PessoaJuridica;
import com.transporte.transportadora.repository.PessoaJuridicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pessoas-juridicas")
public class PessoaJuridicaController {

    @Autowired
    private PessoaJuridicaRepository pessoaJuridicaRepository;

    @GetMapping
    public ResponseEntity<List<PessoaJuridica>> listarTodas() {
        List<PessoaJuridica> pessoasJuridicas = pessoaJuridicaRepository.findAll();
        return ResponseEntity.ok(pessoasJuridicas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PessoaJuridica> buscarPorId(@PathVariable Long id) {
        return pessoaJuridicaRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @PostMapping
    public ResponseEntity<PessoaJuridica> criar(@RequestBody PessoaJuridica pessoaJuridica) {
        PessoaJuridica novaPessoaJuridica = pessoaJuridicaRepository.save(pessoaJuridica);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaPessoaJuridica);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PessoaJuridica> atualizar(@PathVariable Long id, @RequestBody PessoaJuridica pessoaAtualizada) {
        return pessoaJuridicaRepository.findById(id)
                .map(pessoa -> {
                    pessoa.setRazaoSocial(pessoaAtualizada.getRazaoSocial());
                    pessoa.setInscEstadual(pessoaAtualizada.getInscEstadual());
                    pessoa.setCnpj(pessoaAtualizada.getCnpj());
                    PessoaJuridica atualizada = pessoaJuridicaRepository.save(pessoa);
                    return ResponseEntity.ok(atualizada);
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (pessoaJuridicaRepository.existsById(id)) {
            pessoaJuridicaRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
