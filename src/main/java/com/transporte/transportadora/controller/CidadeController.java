package com.transporte.transportadora.controller;

import com.transporte.transportadora.model.Cidade;
import com.transporte.transportadora.repository.CidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cidade")
public class CidadeController {

    @Autowired
    private CidadeRepository cidadeRepository;

    @GetMapping
    public List<Cidade> listarTodas() {
        return cidadeRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Cidade> criarCidade(@RequestBody Cidade cidade) {
        Cidade novaCidade = cidadeRepository.save(cidade);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaCidade);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cidade> atualizarCidade(@PathVariable Long id, @RequestBody Cidade cidadeAtualizada) {
        return cidadeRepository.findById(id).map(cidade -> {
            cidade.setNome(cidadeAtualizada.getNome());
            cidade.setEstado(cidadeAtualizada.getEstado());
            cidade.setPrecoUnitPeso(cidadeAtualizada.getPrecoUnitPeso());
            cidade.setPrecoUnitValor(cidadeAtualizada.getPrecoUnitValor());
            cidadeRepository.save(cidade);
            return ResponseEntity.ok(cidade);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirCidade(@PathVariable Long id) {
        return cidadeRepository.findById(id).map(cidade -> {
            cidadeRepository.delete(cidade);
            return ResponseEntity.noContent().<Void>build(); // Corrigindo para <Void>
        }).orElse(ResponseEntity.notFound().build());
    }

}
