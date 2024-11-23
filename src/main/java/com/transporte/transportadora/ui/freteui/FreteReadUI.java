package com.transporte.transportadora.ui.freteui;

import com.transporte.transportadora.model.Frete;
import com.transporte.transportadora.service.FreteService;
import com.transporte.transportadora.ui.MainUI;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.util.List;

@Lazy
@Component
public class FreteReadUI extends JFrame {

    private JTable tabelaFretes;
    private JButton btnVoltar;

    private final FreteService freteService;
    private final MainUI mainUI;

    public FreteReadUI(FreteService freteService, MainUI mainUI) {
        this.freteService = freteService;
        this.mainUI = mainUI;
        initUI();
    }

    private void initUI() {
        setTitle("Listar Fretes");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        String[] colunas = {"ID", "Data", "Peso", "Valor", "ICMS", "Pedágio", "Origem", "Destino", "Remetente", "Destinatário"};
        Object[][] dados = carregarDadosFretes();
        tabelaFretes = new JTable(dados, colunas);
        JScrollPane scrollPane = new JScrollPane(tabelaFretes);

        add(scrollPane, BorderLayout.CENTER);

        // Botão Voltar
        JPanel panelSul = new JPanel();
        btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(e -> voltarParaMainUI());
        panelSul.add(btnVoltar);
        add(panelSul, BorderLayout.SOUTH);

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

    private void voltarParaMainUI() {
        mainUI.setVisible(true); // Reexibe a MainUI
        dispose(); // Fecha a tela atual
    }
}