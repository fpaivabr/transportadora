package com.transporte.transportadora.service;

import com.transporte.transportadora.repository.CidadeRepository;
import com.transporte.transportadora.repository.FreteRepository;
import com.transporte.transportadora.repository.PessoaJuridicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BuscaService {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private FreteRepository freteRepository;

    @Autowired
    private PessoaJuridicaRepository pessoaJuridicaRepository;

    public List<String[]> buscarArrecadacaoPorEstado(String estado) {
        List<Object[]> resultados = freteRepository.buscarArrecadacaoPorEstado(estado);
        List<String[]> dadosFormatados = new ArrayList<>();

        for (Object[] resultado : resultados) {
            String cidade = (String) resultado[0];
            String nomeEstado = (String) resultado[1];
            Long quantidadeFretes = (Long) resultado[2];
            BigDecimal valorTotal = (BigDecimal) resultado[3];

            dadosFormatados.add(new String[]{
                    cidade, nomeEstado, String.valueOf(quantidadeFretes), String.format("%.2f", valorTotal)
            });
        }

        return dadosFormatados;
    }

    public List<String[]> buscarMediaFretes(String estado) {
        List<Object[]> resultados = cidadeRepository.buscarMediaFretesPorEstado(estado);
        List<String[]> dadosFormatados = new ArrayList<>();

        for (Object[] resultado : resultados) {
            String nomeEstado = (String) resultado[0];
            String cidade = (String) resultado[1];
            BigDecimal mediaOrigem = (BigDecimal) resultado[2];
            BigDecimal mediaDestino = (BigDecimal) resultado[3];

            dadosFormatados.add(new String[]{
                    nomeEstado, cidade, String.format("%.2f", mediaOrigem), String.format("%.2f", mediaDestino)
            });
        }

        return dadosFormatados;
    }

    public List<String[]> buscarFretesAtendidosPorPJ(String mes, String ano) {
        int numeroMes = converterMesParaNumero(mes);
        if (numeroMes == -1) {
            throw new IllegalArgumentException("Mês inválido: " + mes);
        }

        List<Object[]> resultados = pessoaJuridicaRepository.buscarFretesPorPessoaJuridica(numeroMes, Integer.parseInt(ano));
        List<String[]> dadosFormatados = new ArrayList<>();

        for (Object[] resultado : resultados) {
            Long numeroConhecido = (Long) resultado[0];
            Date dataFrete = (Date) resultado[1];
            String razaoSocial = (String) resultado[2];
            String cnpj = (String) resultado[3];
            String representante = (String) resultado[4];

            dadosFormatados.add(new String[]{String.valueOf(numeroConhecido), String.valueOf(dataFrete), razaoSocial, cnpj, representante});
        }

        return dadosFormatados;
    }

    private int converterMesParaNumero(String mes) {
        switch (mes.toLowerCase()) {
            case "janeiro": return 1;
            case "fevereiro": return 2;
            case "março": return 3;
            case "abril": return 4;
            case "maio": return 5;
            case "junho": return 6;
            case "julho": return 7;
            case "agosto": return 8;
            case "setembro": return 9;
            case "outubro": return 10;
            case "novembro": return 11;
            case "dezembro": return 12;
            default: return -1;
        }
    }
}
