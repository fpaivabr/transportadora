package com.transporte.transportadora.model;

import jakarta.persistence.*;

@Entity
@Table(name = "estado")
public class Estado {

    @Id
    @Column(length = 2) // Sigla do estado, como "GO", "SP", etc.
    private String uf;

    @Column(nullable = false, length = 100)
    private String nomeEst;

    @Column(nullable = false)
    private Double icmsLocal;

    @Column(nullable = false)
    private Double icmsOutroUf;

    // Getters e Setters
    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getNomeEst() {
        return nomeEst;
    }

    public void setNomeEst(String nomeEst) {
        this.nomeEst = nomeEst;
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

