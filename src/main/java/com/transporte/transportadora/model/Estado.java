package com.transporte.transportadora.model;

import jakarta.persistence.*;

@Entity
@Table(name = "estado")
public class Estado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(name = "icms_local", nullable = false)
    private Double icmsLocal;

    @Column(name = "icms_outro_uf", nullable = false)
    private Double icmsOutroUf;

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

    public Double getIcmsLocal() {
        return icmsLocal;
    }

    public void setIcmsLocal(Double icmsLocal) {
        this.icmsLocal = icmsLocal;
    }

    public Double getIcmsOutroUf() {
        return icmsOutroUf;
    }

    public void setIcmsOutroUf(Double icmsOutroUf) {
        this.icmsOutroUf = icmsOutroUf;
    }
}
