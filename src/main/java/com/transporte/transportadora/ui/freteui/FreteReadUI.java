package com.transporte.transportadora.ui.freteui;

import com.transporte.transportadora.model.Frete;
import com.transporte.transportadora.service.FreteService;
import com.transporte.transportadora.service.impl.FreteServiceImpl;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class FreteReadUI extends JFrame {

    private JTable tabelaFretes;
    private FreteService freteService;

    public FreteReadUI() {
        freteService = new FreteServiceImpl();

        setTitle("Listar Fretes");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Criar tabela
        String[] colunas = {"ID", "Data", "Peso", "Valor", "ICMS", "Pedágio", "Origem", "Destino", "Remetente", "Destinatário"};
        Object[][] dados = carregarDadosFretes();
        tabelaFretes = new JTable(dados, colunas);
        JScrollPane scrollPane = new JScrollPane(tabelaFretes);

        add(scrollPane, BorderLayout.CENTER);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private Object[][] carregarDadosFretes() {
        try {
            List<Frete> fretes = freteService.listarTodos();
            Object[][] dados = new Object[fretes.size()][10];
            for (int i = 0; i < fretes.size(); i++) {
                Frete frete = fretes.get(i);
                dados[i][0] = frete.getNumConhec();
                dados[i][1] = frete.getDataFrete();
                dados[i][2] = frete.getPeso();
                dados[i][3] = frete.getValor();
                dados[i][4] = frete.getIcms();
                dados[i][5] = frete.getPedagio();
                dados[i][6] = frete.getOrigem().getNome();
                dados[i][7] = frete.getDestino().getNome();
                dados[i][8] = frete.getRemetente().getPessoaFisica() != null ? frete.getRemetente().getPessoaFisica().getNomeCli() : frete.getRemetente().getPessoaJuridica().getRazaoSocial();
                dados[i][9] = frete.getDestinatario().getPessoaFisica() != null ? frete.getDestinatario().getPessoaFisica().getNomeCli() : frete.getDestinatario().getPessoaJuridica().getRazaoSocial();
            }
            return dados;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar dados: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            return new Object[0][0];
        }
    }

    public static void main(String[] args) {
        new FreteReadUI();
    }
}
