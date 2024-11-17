package com.transporte.transportadora.model;

import jakarta.persistence.*;

@Entity
@Table(name = "cidade")
public class Cidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigoCid;

    @Column(nullable = false, length = 100)
    private String nomeCid;

    @ManyToOne
    @JoinColumn(name = "uf", nullable = false)
    private Estado estado;

    // Getters e Setters
    public Long getCodigoCid() {
        return codigoCid;
    }

    public void setCodigoCid(Long codigoCid) {
        this.codigoCid = codigoCid;
    }

    public String getNomeCid() {
        return nomeCid;
    }

    public void setNomeCid(String nomeCid) {
        this.nomeCid = nomeCid;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }
}

