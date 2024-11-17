package com.transporte.transportadora.model;

import jakarta.persistence.*;

@Entity
@Table(name = "pessoa_fisica")
public class PessoaFisica {

    @Id
    private Long codCli; // Mesma chave primária de Cliente

    @OneToOne
    @MapsId
    @JoinColumn(name = "cod_cli")
    private Cliente cliente;

    @Column(nullable = false)
    private String nomeCli;

    @Column(nullable = false, unique = true, length = 11)
    private String cpf;

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

    public String getNomeCli() {
        return nomeCli;
    }

    public void setNomeCli(String nomeCli) {
        this.nomeCli = nomeCli;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}

