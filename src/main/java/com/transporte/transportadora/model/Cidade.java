package com.transporte.transportadora.model;

import jakarta.persistence.*;

@Entity
@Table(name = "cidade")
public class Cidade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @ManyToOne
    @JoinColumn(name = "estado_id", nullable = false)
    private Estado estado;

    @Column(name = "preco_unit_peso", nullable = false)
    private Double precoUnitPeso;

    @Column(name = "preco_unit_valor", nullable = false)
    private Double precoUnitValor;

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Double getPrecoUnitPeso() {
        return precoUnitPeso;
    }

    public void setPrecoUnitPeso(Double precoUnitPeso) {
        this.precoUnitPeso = precoUnitPeso;
    }

    public Double getPrecoUnitValor() {
        return precoUnitValor;
    }

    public void setPrecoUnitValor(Double precoUnitValor) {
        this.precoUnitValor = precoUnitValor;
    }
}
