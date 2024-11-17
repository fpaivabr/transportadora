package com.transporte.transportadora.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "frete")
public class Frete {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long numConhec;

    @Column(nullable = false)
    private LocalDate dataFrete;

    @Column(nullable = false)
    private Double peso;

    @Column(nullable = false)
    private Double valor;

    @Column(nullable = false)
    private Double icms;

    @Column(nullable = false)
    private Double pedagio;

    @ManyToOne
    @JoinColumn(name = "remetente", nullable = false)
    private Cliente remetente;

    @ManyToOne
    @JoinColumn(name = "destinatario", nullable = false)
    private Cliente destinatario;

    @ManyToOne
    @JoinColumn(name = "codigo_origem", nullable = false)
    private Cidade origem;

    @ManyToOne
    @JoinColumn(name = "codigo_destino", nullable = false)
    private Cidade destino;

    // Getters e Setters
    public Long getNumConhec() {
        return numConhec;
    }

    public void setNumConhec(Long numConhec) {
        this.numConhec = numConhec;
    }

    public LocalDate getDataFrete() {
        return dataFrete;
    }

    public void setDataFrete(LocalDate dataFrete) {
        this.dataFrete = dataFrete;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Double getIcms() {
        return icms;
    }

    public void setIcms(Double icms) {
        this.icms = icms;
    }

    public Double getPedagio() {
        return pedagio;
    }

    public void setPedagio(Double pedagio) {
        this.pedagio = pedagio;
    }

    public Cliente getRemetente() {
        return remetente;
    }

    public void setRemetente(Cliente remetente) {
        this.remetente = remetente;
    }

    public Cliente getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(Cliente destinatario) {
        this.destinatario = destinatario;
    }

    public Cidade getOrigem() {
        return origem;
    }

    public void setOrigem(Cidade origem) {
        this.origem = origem;
    }

    public Cidade getDestino() {
        return destino;
    }

    public void setDestino(Cidade destino) {
        this.destino = destino;
    }
}

