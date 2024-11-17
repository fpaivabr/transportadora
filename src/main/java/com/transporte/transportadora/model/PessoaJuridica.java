package com.transporte.transportadora.model;

import jakarta.persistence.*;

@Entity
@Table(name = "pessoa_juridica")
public class PessoaJuridica {

    @Id
    private Long codCli;

    @OneToOne
    @MapsId
    @JoinColumn(name = "cod_cli")
    private Cliente cliente;

    @Column(nullable = false)
    private String razaoSocial;

    @Column(nullable = false)
    private String inscEstadual;

    @Column(nullable = false, unique = true, length = 14)
    private String cnpj;

    // Getters e Setters
    public Long getCodCli() {
        return codCli;
    }

    public void setCodCli(Long codCli) {
        this.codCli = codCli;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getInscEstadual() {
        return inscEstadual;
    }

    public void setInscEstadual(String inscEstadual) {
        this.inscEstadual = inscEstadual;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
}
